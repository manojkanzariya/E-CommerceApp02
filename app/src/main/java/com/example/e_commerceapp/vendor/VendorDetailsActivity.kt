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
import com.example.e_commerceapp.databinding.ActivityVendorDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
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

class VendorDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVendorDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var selectedImageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var uid: String
    private lateinit var email: String
    private var isNewRegistration: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendorDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Get data from intent
        uid = intent.getStringExtra("uid") ?: auth.currentUser?.uid ?: run {
            finish()
            return
        }
        email = intent.getStringExtra("email") ?: auth.currentUser?.email ?: ""
        isNewRegistration = intent.getBooleanExtra("REGISTER_AS_VENDOR", false)

        setupListeners()
        loadExistingVendorData()
    }

    private fun loadExistingVendorData() {
        db.collection("vendors").document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    binding.storeName.setText(document.getString("storeName"))
                    binding.storeDescription.setText(document.getString("storeDescription"))
                    binding.gstNumber.setText(document.getString("gstNumber"))
                    binding.address.setText(document.getString("address"))
                    binding.city.setText(document.getString("city"))
                    binding.pincode.setText(document.getString("pincode"))

                    document.getString("imageUrl")?.let { imageUrl ->
                        Glide.with(this).load(imageUrl).into(binding.storeImage)
                    }
                }
            }
    }

    private fun setupListeners() {
        binding.uploadImageBtn.setOnClickListener {
            openImagePicker()
        }

        binding.completeRegistrationBtn.setOnClickListener {
            completeRegistration()
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
                Glide.with(this).load(it).into(binding.storeImage)
            }
        }
    }

    private fun completeRegistration() {
        val storeName = binding.storeName.text.toString().trim()
        val storeDescription = binding.storeDescription.text.toString().trim()
        val gstNumber = binding.gstNumber.text.toString().trim()
        val address = binding.address.text.toString().trim()
        val city = binding.city.text.toString().trim()
        val pincode = binding.pincode.text.toString().trim()

        if (!validateInputs(storeName, storeDescription, gstNumber, address, city, pincode)) {
            return
        }

        val progressDialog = ProgressDialog(this).apply {
            setMessage("Completing registration...")
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
                        registerVendor(storeName, storeDescription, gstNumber, address, city, pincode, imageUrl, progressDialog)
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(
                            this@VendorDetailsActivity,
                            "Image upload failed: ${response.message()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<CloudinaryResponse>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(
                        this@VendorDetailsActivity,
                        "Image upload failed: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("CloudinaryUpload", "Failure: ${t.message}", t)
                }
            })
        } else {
            registerVendor(storeName, storeDescription, gstNumber, address, city, pincode, null, progressDialog)
        }
    }

    private fun registerVendor(
        storeName: String,
        storeDescription: String,
        gstNumber: String,
        address: String,
        city: String,
        pincode: String,
        imageUrl: String?,
        progressDialog: ProgressDialog
    ) {
        val vendorData = hashMapOf(
            "uid" to uid,
            "email" to email,
            "storeName" to storeName,
            "storeDescription" to storeDescription,
            "gstNumber" to gstNumber,
            "address" to address,
            "city" to city,
            "pincode" to pincode,
            "imageUrl" to imageUrl,
            "createdAt" to System.currentTimeMillis(),
            "updatedAt" to System.currentTimeMillis()
        )

        // Transaction to ensure data consistency
        db.runTransaction { transaction ->
            if (isNewRegistration) {
                val userRef = db.collection("users").document(uid)
                transaction.update(userRef, "roles", FieldValue.arrayUnion("vendor"))
                transaction.update(userRef, "currentRole", "vendor")
            }

            // Set vendor data
            val vendorRef = db.collection("vendors").document(uid)
            transaction.set(vendorRef, vendorData)
        }.addOnSuccessListener {
            progressDialog.dismiss()
            navigateToDashboard()
        }.addOnFailureListener { e ->
            progressDialog.dismiss()
            Toast.makeText(
                this,
                "Registration failed: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
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

    private fun navigateToDashboard() {
        val intent = Intent(this, VendorDashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun validateInputs(
        storeName: String,
        storeDescription: String,
        gstNumber: String,
        address: String,
        city: String,
        pincode: String
    ): Boolean {
        var isValid = true

        if (storeName.isEmpty()) {
            binding.storeNameLayout.error = "Store name is required"
            isValid = false
        } else {
            binding.storeNameLayout.error = null
        }

        if (storeDescription.isEmpty()) {
            binding.storeDescriptionLayout.error = "Store description is required"
            isValid = false
        } else {
            binding.storeDescriptionLayout.error = null
        }

        if (gstNumber.isEmpty()) {
            binding.gstNumberLayout.error = "GST number is required"
            isValid = false
        } else {
            binding.gstNumberLayout.error = null
        }

        if (address.isEmpty()) {
            binding.addressLayout.error = "Address is required"
            isValid = false
        } else {
            binding.addressLayout.error = null
        }

        if (city.isEmpty()) {
            binding.cityLayout.error = "City is required"
            isValid = false
        } else {
            binding.cityLayout.error = null
        }

        if (pincode.isEmpty()) {
            binding.pincodeLayout.error = "Pincode is required"
            isValid = false
        } else {
            binding.pincodeLayout.error = null
        }

        return isValid
    }
}