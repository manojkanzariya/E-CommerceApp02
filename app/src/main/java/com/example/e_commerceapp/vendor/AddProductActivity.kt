package com.example.e_commerceapp.vendor

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.CloudinaryResponse
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.data.repository.CloudinaryClient
import com.google.android.material.textfield.TextInputLayout
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

class AddProductActivity : AppCompatActivity() {

    private lateinit var ivProductImage: ImageView
    private lateinit var tilName: TextInputLayout
    private lateinit var tilShortDesc: TextInputLayout
    private lateinit var tilFullDesc: TextInputLayout
    private lateinit var tilPrice: TextInputLayout
    private lateinit var tilOriginalPrice: TextInputLayout
    private lateinit var tilCategory: TextInputLayout
    private lateinit var tilStock: TextInputLayout
    private lateinit var switchInStock: Switch

    private var imageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        initViews()

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        findViewById<Button>(R.id.btnSelectImage).setOnClickListener {
            openImagePicker()
        }

        findViewById<Button>(R.id.btnAddProduct).setOnClickListener {
            checkVendorPaymentSetup {
                validateAndUploadProduct()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initViews() {
        ivProductImage = findViewById(R.id.ivProductImage)
        tilName = findViewById(R.id.tilName)
        tilShortDesc = findViewById(R.id.tilShortDesc)
        tilFullDesc = findViewById(R.id.tilFullDesc)
        tilPrice = findViewById(R.id.tilPrice)
        tilOriginalPrice = findViewById(R.id.tilOriginalPrice)
        tilCategory = findViewById(R.id.tilCategory)
        tilStock = findViewById(R.id.tilStock)
        switchInStock = findViewById(R.id.switchInStock)
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
                imageUri = it
                ivProductImage.setImageURI(it)
            }
        }
    }

    private fun checkVendorPaymentSetup(onSuccess: () -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        FirebaseFirestore.getInstance()
            .collection("vendor_payments")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Check if at least one payment method is properly set up
                    val upiEnabled = document.getBoolean("upiEnabled") ?: false
                    val bankTransferEnabled = document.getBoolean("bankTransferEnabled") ?: false

                    if (upiEnabled || bankTransferEnabled) {
                        // For UPI, check if UPI ID is provided
                        val upiValid = !upiEnabled ||
                                (document.getString("upiId")?.isNotEmpty() == true &&
                                        isValidUpiId(document.getString("upiId") ?: ""))

                        // For Bank Transfer, check all required fields
                        val bankTransferValid = !bankTransferEnabled ||
                                (document.getString("accountNumber")?.isNotEmpty() == true &&
                                        document.getString("bankName")?.isNotEmpty() == true &&
                                        document.getString("accountHolderName")?.isNotEmpty() == true &&
                                        document.getString("ifscCode")?.isNotEmpty() == true &&
                                        isValidIfsc(document.getString("ifscCode") ?: ""))

                        if ((!upiEnabled || upiValid) && (!bankTransferEnabled || bankTransferValid)) {
                            // Payment info is properly set up
                            onSuccess()
                            return@addOnSuccessListener
                        }
                    }
                }

                // If we get here, payment info is not properly set up
                Toast.makeText(this, "Please complete your payment details before adding a product.", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, VendorPaymentActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error checking payment setup", Toast.LENGTH_SHORT).show()
            }
    }

    private fun isValidUpiId(upiId: String): Boolean {
        return upiId.matches(Regex("^[a-zA-Z0-9._-]+@[a-zA-Z0-9]+$"))
    }

    private fun isValidIfsc(ifscCode: String): Boolean {
        return ifscCode.matches(Regex("^[A-Z]{4}0[A-Z0-9]{6}$"))
    }

    private fun validateAndUploadProduct() {
        val name = tilName.editText?.text.toString().trim()
        val shortDesc = tilShortDesc.editText?.text.toString().trim()
        val fullDesc = tilFullDesc.editText?.text.toString().trim()
        val price = tilPrice.editText?.text.toString().toDoubleOrNull() ?: 0.0
        val originalPrice = tilOriginalPrice.editText?.text.toString().toDoubleOrNull() ?: price
        val category = tilCategory.editText?.text.toString().trim()
        val stock = tilStock.editText?.text.toString().toIntOrNull() ?: 1
        val inStock = switchInStock.isChecked

        if (imageUri == null) {
            showToast("Please select an image")
            return
        }

        val progressDialog = ProgressDialog(this).apply {
            setMessage("Uploading product...")
            setCancelable(false)
            show()
        }

        val file = uriToFile(imageUri!!) ?: run {
            progressDialog.dismiss()
            showToast("File error")
            return
        }

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestBody)
        val uploadPreset = "your_unsigned_preset".toRequestBody("text/plain".toMediaTypeOrNull())

        CloudinaryClient.service.uploadImage(uploadPreset, body).enqueue(object : Callback<CloudinaryResponse> {
            override fun onResponse(call: Call<CloudinaryResponse>, response: Response<CloudinaryResponse>) {
                if (response.isSuccessful) {
                    val imageUrl = response.body()?.secure_url ?: ""
                    val uid = FirebaseAuth.getInstance().currentUser?.uid
                    if (uid.isNullOrEmpty()) {
                        progressDialog.dismiss()
                        showToast("Vendor not logged in")
                        return
                    }

                    val product = Product(
                        name = name,
                        shortDescription = shortDesc,
                        fullDescription = fullDesc,
                        price = price,
                        originalPrice = originalPrice,
                        category = category,
                        stock = stock,
                        inStock = inStock,
                        vendorId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                        quantity = 1,
                        imageUrl = imageUrl,
                        productId = ""
                    )

                    FirebaseFirestore.getInstance().collection("products")
                        .add(product)
                        .addOnSuccessListener { documentReference ->
                            documentReference.update(
                                "id", documentReference.id,
                                "productId", documentReference.id
                            )
                            progressDialog.dismiss()
                            showToast("Product added successfully")
                            finish()
                        }
                        .addOnFailureListener {
                            progressDialog.dismiss()
                            showToast("Upload failed: ${it.message}")
                            Log.e("CloudinaryUpload", "Failure: ${it.message}", it)
                        }
                } else {
                    progressDialog.dismiss()
                    showToast("Upload failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CloudinaryResponse>, t: Throwable) {
                progressDialog.dismiss()
                showToast("Upload failed: ${t.message}")
            }
        })
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

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}