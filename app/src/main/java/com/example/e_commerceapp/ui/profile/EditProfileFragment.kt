package com.example.e_commerceapp.ui.profile

import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.e_commerceapp.data.model.CloudinaryResponse
import com.example.e_commerceapp.data.model.User
import com.example.e_commerceapp.data.repository.CloudinaryClient
import com.example.e_commerceapp.data.repository.UserRepository
import com.example.e_commerceapp.databinding.FragmentEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private var selectedImageUri: Uri? = null
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            selectedImageUri = it
            binding.ivProfileImage.setImageURI(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        loadUserData()
    }

    private fun setupViews() {
        binding.btnSaveProfile.setOnClickListener {
            saveUserData()
            findNavController().popBackStack()
        }

        binding.btnChangePicture.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.ivProfileImage.setOnClickListener {
            pickImage.launch("image/*")
        }
    }

    private fun loadUserData() {
        viewLifecycleOwner.lifecycleScope.launch {
            val user = UserRepository.getUser()
            user?.let {
                binding.etUserName.setText(it.name)
                binding.etUserEmail.setText(it.email)

                it.profileImage?.let { imageUri ->
                    if (imageUri.startsWith("http")) {
                        Glide.with(requireContext())
                            .load(imageUri)
                            .into(binding.ivProfileImage)
                    } else {
                        binding.ivProfileImage.setImageURI(Uri.parse(imageUri))
                    }
                }
            }
        }
    }


    private fun saveUserData() {
        val newName = binding.etUserName.text.toString().trim()
        val newEmail = binding.etUserEmail.text.toString().trim()

        if (newName.isEmpty()) {
            binding.etUserName.error = "Name cannot be empty"
            return
        }

        if (newEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            binding.etUserEmail.error = "Enter a valid email"
            return
        }

        binding.btnSaveProfile.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE

        viewLifecycleOwner.lifecycleScope.launch {
            val existingImage = UserRepository.getUser()?.profileImage

            if (selectedImageUri != null) {
                uploadImageToCloudinary(selectedImageUri!!) { imageUrl ->
                    saveUserProfile(newName, newEmail, imageUrl ?: existingImage)
                }
            } else {
                saveUserProfile(newName, newEmail, existingImage)
            }
        }
    }


    private fun uploadImageToCloudinary(uri: Uri, callback: (String?) -> Unit) {
        val file = uriToFile(uri) ?: run {
            callback(null)
            return
        }

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestBody)
        val uploadPreset = "your_unsigned_preset".toRequestBody("text/plain".toMediaTypeOrNull())

        CloudinaryClient.service.uploadImage(uploadPreset, body).enqueue(object : Callback<CloudinaryResponse> {
            override fun onResponse(call: Call<CloudinaryResponse>, response: Response<CloudinaryResponse>) {
                if (response.isSuccessful) {
                    callback(response.body()?.secure_url)
                } else {
                    Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show()
                    callback(null)
                }
            }

            override fun onFailure(call: Call<CloudinaryResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Image upload failed: ${t.message}", Toast.LENGTH_SHORT).show()
                callback(null)
            }
        })
    }

    private fun saveUserProfile(name: String, email: String, imageUrl: String?) {
        viewLifecycleOwner.lifecycleScope.launch {
            val currentUser = UserRepository.getUser()

            val user = User(
                name = name,
                email = email,
                profileImage = imageUrl ?: currentUser?.profileImage,
                address = currentUser?.address
            )

            // Update local repository first
            UserRepository.setUser(user)

            auth.currentUser?.uid?.let { uid ->
                firestore.collection("users").document(uid)
                    .update(
                        mapOf(
                            "name" to name,
                            "email" to email,
                            "profileImage" to user.profileImage
                        )
                    )
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                        parentFragmentManager.popBackStack()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(requireContext(), "Failed to update profile: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                    .addOnCompleteListener {
                        binding.btnSaveProfile.isEnabled = true
                        binding.progressBar.visibility = View.GONE
                    }
            } ?: run {
                Toast.makeText(requireContext(), "Profile updated locally", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
                binding.btnSaveProfile.isEnabled = true
                binding.progressBar.visibility = View.GONE
            }
        }
    }


    private fun uriToFile(uri: Uri): File? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri) ?: return null
            val file = File.createTempFile("profile_", ".jpg", requireContext().cacheDir)
            FileOutputStream(file).use { output -> inputStream.copyTo(output) }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}