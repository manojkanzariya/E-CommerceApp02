package com.example.e_commerceapp.vendor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.databinding.ActivityInventoryBinding
import com.example.e_commerceapp.vendor.adapter.InventoryAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class InventoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val productList = mutableListOf<Product>()
    private lateinit var adapter: InventoryAdapter
    private lateinit var binding: ActivityInventoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerInventory)
        adapter = InventoryAdapter(productList,
            onDelete = { deleteProduct(it) },
            onEdit = { editProduct(it) }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        setupToolbar()
        fetchProducts()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Inventory"
        }
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun fetchProducts() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        FirebaseFirestore.getInstance()
            .collection("products")
            .whereEqualTo("vendorId", uid)
            .get()
            .addOnSuccessListener { result ->
                productList.clear()
                for (doc in result) {
                    val product = Product(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        price = doc.getDouble("price") ?: 0.0, // Changed to getDouble
                        originalPrice = doc.getDouble("originalPrice") ?: doc.getDouble("price") ?: 0.0,
                        shortDescription = doc.getString("shortDescription") ?: "",
                        fullDescription = doc.getString("fullDescription") ?: "",
                        rating = doc.getDouble("rating")?.toFloat() ?: 0.0f,
                        ratingCount = doc.getLong("ratingCount")?.toInt() ?: 0,
                        category = doc.getString("category") ?: "",
                        imageUrl = doc.getString("imageUrl") ?: "",
                        stock = doc.getLong("stock")?.toInt() ?: 1,
                        quantity = doc.getLong("quantity")?.toInt() ?: 1,
                        inStock = doc.getBoolean("inStock") ?: true
                    )
                    productList.add(product)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
            Log.e("FetchProducts", "Error loading products", e)
            Toast.makeText(this, "Failed to load inventory: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteProduct(product: Product) {
        FirebaseFirestore.getInstance().collection("products").document(product.id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Product deleted", Toast.LENGTH_SHORT).show()
                fetchProducts()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show()
            }
    }

    private fun editProduct(product: Product) {
        // You can pass the product to an EditProductActivity
        val intent = Intent(this, EditProductActivity::class.java)
        intent.putExtra("productId", product.id)
        startActivity(intent)
    }
}
