package com.example.e_commerceapp.vendor

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.CloudinaryResponse
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.data.repository.CloudinaryClient
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class EditProductActivity : AppCompatActivity() {

    // Views
    private lateinit var tilName: TextInputLayout
    private lateinit var tilShortDesc: TextInputLayout
    private lateinit var tilFullDesc: TextInputLayout
    private lateinit var tilPrice: TextInputLayout
    private lateinit var tilOriginalPrice: TextInputLayout
    private lateinit var tilCategory: TextInputLayout
    private lateinit var tilStock: TextInputLayout
    private lateinit var switchInStock: Switch
    private lateinit var ivImage: ImageView
    private lateinit var btnUpdate: Button
    private lateinit var btnSelectImage: Button

    // Variables
    private var imageUri: Uri? = null
    private var imageChanged = false
    private lateinit var productId: String
    private lateinit var currentProduct: Product
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        initViews()
        productId = intent.getStringExtra("productId") ?: ""

        if (productId.isEmpty()) {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnUpdate.setOnClickListener {
            updateProduct()
        }

        loadProductData()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initViews() {
        tilName = findViewById(R.id.tilName)
        tilShortDesc = findViewById(R.id.tilShortDesc)
        tilFullDesc = findViewById(R.id.tilFullDesc)
        tilPrice = findViewById(R.id.tilPrice)
        tilOriginalPrice = findViewById(R.id.tilOriginalPrice)
        tilCategory = findViewById(R.id.tilCategory)
        tilStock = findViewById(R.id.tilStock)
        switchInStock = findViewById(R.id.switchInStock)
        ivImage = findViewById(R.id.ivProductImage)
        btnUpdate = findViewById(R.id.btnUpdateProduct)
        btnSelectImage = findViewById(R.id.btnSelectImage)

        progressDialog = ProgressDialog(this).apply {
            setMessage("Updating product...")
            setCancelable(false)
        }
    }

    private fun loadProductData() {
        progressDialog.show()
        FirebaseFirestore.getInstance().collection("products")
            .document(productId)
            .get()
            .addOnSuccessListener { doc ->
                currentProduct = doc.toObject(Product::class.java) ?: Product()
                currentProduct.id = doc.id

                // Populate fields
                tilName.editText?.setText(currentProduct.name)
                tilShortDesc.editText?.setText(currentProduct.shortDescription)
                tilFullDesc.editText?.setText(currentProduct.fullDescription)
                tilPrice.editText?.setText(currentProduct.price.toString())
                tilOriginalPrice.editText?.setText(currentProduct.originalPrice.toString())
                tilCategory.editText?.setText(currentProduct.category)
                tilStock.editText?.setText(currentProduct.stock.toString())
                switchInStock.isChecked = currentProduct.inStock

                // Load image
                Glide.with(this)
                    .load(currentProduct.imageUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(ivImage)

                progressDialog.dismiss()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to load product", Toast.LENGTH_SHORT).show()
                finish()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            ivImage.setImageURI(imageUri)
            imageChanged = true
        }
    }

    private fun updateProduct() {
        val name = tilName.editText?.text.toString().trim()
        val shortDesc = tilShortDesc.editText?.text.toString().trim()
        val fullDesc = tilFullDesc.editText?.text.toString().trim()
        val price = tilPrice.editText?.text.toString().trim()
        val originalPrice = tilOriginalPrice.editText?.text.toString().trim()
        val category = tilCategory.editText?.text.toString().trim()
        val stock = tilStock.editText?.text.toString().trim()
        val inStock = switchInStock.isChecked

        // Validate fields
        if (validateFields(name, shortDesc, fullDesc, price, originalPrice, category, stock)) {
            progressDialog.show()

            val updatedProduct = currentProduct.copy(
                name = name,
                shortDescription = shortDesc,
                fullDescription = fullDesc,
                price = price.toDouble(),
                originalPrice = originalPrice.toDouble(),
                category = category,
                stock = stock.toInt(),
                inStock = inStock,
                timestamp = Timestamp.now(),
                vendorId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                quantity = 1,
                imageUrl = currentProduct.imageUrl,
                productId = currentProduct.productId
            )

            if (imageChanged && imageUri != null) {
                uploadImageAndUpdate(updatedProduct)
            } else {
                updateFirestore(updatedProduct, null)
            }
        }
    }

    private fun validateFields(name: String,
        shortDesc: String,
        fullDesc: String,
        price: String,
        originalPrice: String,
        category: String,
        stock: String
    ): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            tilName.error = "Product name required"
            isValid = false
        } else {
            tilName.error = null
        }

        if (shortDesc.isEmpty()) {
            tilShortDesc.error = "Short description required"
            isValid = false
        } else {
            tilShortDesc.error = null
        }

        if (fullDesc.isEmpty()) {
            tilFullDesc.error = "Full description required"
            isValid = false
        } else {
            tilFullDesc.error = null
        }

        if (price.isEmpty()) {
            tilPrice.error = "Price required"
            isValid = false
        } else {
            tilPrice.error = null
        }

        if (originalPrice.isEmpty()) {
            tilOriginalPrice.error = "Original price required"
            isValid = false
        } else {
            tilOriginalPrice.error = null
        }

        if (category.isEmpty()) {
            tilCategory.error = "Category required"
            isValid = false
        } else {
            tilCategory.error = null
        }

        if (stock.isEmpty()) {
            tilStock.error = "Stock quantity required"
            isValid = false
        } else {
            tilStock.error = null
        }

        return isValid
    }

    private fun uploadImageAndUpdate(product: Product) {
        val file = File(imageUri!!.path!!)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val presetBody = "your_unsigned_preset".toRequestBody("text/plain".toMediaTypeOrNull())

        CloudinaryClient.service.uploadImage(presetBody, body)
            .enqueue(object : Callback<CloudinaryResponse> {
                override fun onResponse(call: Call<CloudinaryResponse>, response: Response<CloudinaryResponse>) {
                    if (response.isSuccessful) {
                        val cloudinaryUrl = response.body()?.secure_url
                        updateFirestore(product, cloudinaryUrl)
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(this@EditProductActivity, "Cloudinary upload failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CloudinaryResponse>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(this@EditProductActivity, "Upload error: ${t.message}", Toast.LENGTH_SHORT).show()
                    Log.e("CloudinaryUpload", "Failure: ${t.message}", t)
                }
            })
    }


    private fun updateFirestore(product: Product, imageUrl: String?) {
        val updates = hashMapOf<String, Any>(
            "name" to product.name,
            "shortDescription" to product.shortDescription,
            "fullDescription" to product.fullDescription,
            "price" to product.price,
            "originalPrice" to product.originalPrice,
            "category" to product.category,
            "stock" to product.stock,
            "inStock" to product.inStock,
            "timestamp" to product.timestamp,
            "vendorId" to product.vendorId,
            "quantity" to product.quantity,
            "productId" to product.productId
        )

        if (imageUrl != null) {
            updates["imageUrl"] = imageUrl
        }

        FirebaseFirestore.getInstance().collection("products")
            .document(product.id)
            .update(updates)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Update failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}