package com.example.e_commerceapp.vendor

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.e_commerceapp.data.model.CloudinaryResponse
import com.example.e_commerceapp.data.repository.CloudinaryClient
import com.example.e_commerceapp.databinding.ActivityVendorProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class VendorProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVendorProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var selectedImageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendorProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        setupToolbar()
        loadVendorData()
        setupClickListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        supportActionBar?.title = "Vendor Profile"
    }

    private fun loadVendorData() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("vendors").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Store Information
                    binding.etStoreName.setText(document.getString("storeName") ?: "")
                    binding.etStoreDescription.setText(document.getString("storeDescription") ?: "")
                    binding.etGstNumber.setText(document.getString("gstNumber") ?: "")

                    // Load store image
                    document.getString("imageUrl")?.let { imageUrl ->
                        Glide.with(this).load(imageUrl).into(binding.ivStoreImage)
                    }

                    // Personal Information
                    binding.etName.setText(auth.currentUser?.displayName ?: "")
                    binding.etEmail.setText(auth.currentUser?.email ?: "")
                    binding.etPhone.setText(document.getString("phone") ?: "")

                    // Address Information
                    binding.etAddress.setText(document.getString("address") ?: "")
                    binding.etCity.setText(document.getString("city") ?: "")
                    binding.etPincode.setText(document.getString("pincode") ?: "")
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load vendor data", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupClickListeners() {
        binding.btnChangeImage.setOnClickListener {
            openImagePicker()
        }

        binding.btnSave.setOnClickListener {
            saveVendorData()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            data?.data?.let {
                selectedImageUri = it
                Glide.with(this).load(it).into(binding.ivStoreImage)
            }
        }
    }

    private fun saveVendorData() {
        val userId = auth.currentUser?.uid ?: return

        // Validate required fields
        val storeName = binding.etStoreName.text.toString().trim()
        val gstNumber = binding.etGstNumber.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()
        val city = binding.etCity.text.toString().trim()
        val pincode = binding.etPincode.text.toString().trim()

        if (storeName.isEmpty() || gstNumber.isEmpty() || phone.isEmpty() ||
            address.isEmpty() || city.isEmpty() || pincode.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val progressDialog = ProgressDialog(this).apply {
            setMessage("Saving vendor profile...")
            setCancelable(false)
            show()
        }

        if (selectedImageUri != null) {
            val file = uriToFile(selectedImageUri!!) ?: run {
                progressDialog.dismiss()
                Toast.makeText(this, "File error", Toast.LENGTH_SHORT).show()
                return
            }

            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestBody)
            val uploadPreset = "your_unsigned_preset".toRequestBody("text/plain".toMediaTypeOrNull())

            CloudinaryClient.service.uploadImage(uploadPreset, body).enqueue(object : Callback<CloudinaryResponse> {
                override fun onResponse(call: Call<CloudinaryResponse>, response: Response<CloudinaryResponse>) {
                    if (response.isSuccessful) {
                        val imageUrl = response.body()?.secure_url ?: ""
                        saveVendorDetails(userId, imageUrl, progressDialog)
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(
                            this@VendorProfileActivity,
                            "Image upload failed: ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<CloudinaryResponse>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(
                        this@VendorProfileActivity,
                        "Image upload failed: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("CloudinaryUpload", "Failure: ${t.message}", t)
                }
            })
        } else {
            saveVendorDetails(userId, null, progressDialog)
        }
    }

    private fun saveVendorDetails(userId: String, imageUrl: String?, progressDialog: ProgressDialog) {
        val vendorData = hashMapOf(
            "storeName" to binding.etStoreName.text.toString().trim(),
            "storeDescription" to binding.etStoreDescription.text.toString().trim(),
            "gstNumber" to binding.etGstNumber.text.toString().trim(),
            "phone" to binding.etPhone.text.toString().trim(),
            "address" to binding.etAddress.text.toString().trim(),
            "city" to binding.etCity.text.toString().trim(),
            "pincode" to binding.etPincode.text.toString().trim(),
            "updatedAt" to System.currentTimeMillis()
        )

        // Add image URL if available
        imageUrl?.let {
            vendorData["imageUrl"] = it
        }

        db.collection("vendors").document(userId)
            .update(vendorData as Map<String, Any>)
            .addOnSuccessListener {
                // Update user display name if changed
                val newName = binding.etName.text.toString().trim()
                auth.currentUser?.updateProfile(
                    com.google.firebase.auth.UserProfileChangeRequest.Builder()
                        .setDisplayName(newName)
                        .build()
                )

                progressDialog.dismiss()
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to update profile: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("FirestoreUpdate", "Failure: ${e.message}", e)
            }
    }

    private fun uriToFile(uri: Uri): File? {
        return try {
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val file = File.createTempFile("upload_", ".jpg", cacheDir)
            FileOutputStream(file).use { output -> inputStream.copyTo(output) }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}