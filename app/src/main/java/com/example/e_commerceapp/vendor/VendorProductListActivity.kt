package com.example.e_commerceapp.vendor

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.databinding.ActivityVendorProductListBinding
import com.example.e_commerceapp.vendor.adapter.VendorProductAdapter
import com.google.firebase.firestore.FirebaseFirestore

class VendorProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVendorProductListBinding
    private lateinit var adapter: VendorProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendorProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        loadProductList()
        setupFab()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "My Products"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun loadProductList() {
        val db = FirebaseFirestore.getInstance()

        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                val productList = mutableListOf<Product>()
                for (document in result) {
                    val product = document.toObject(Product::class.java)
                    productList.add(product)
                }

                adapter = VendorProductAdapter(productList) { selectedProduct ->
                    Toast.makeText(this, "Clicked: ${selectedProduct.name}", Toast.LENGTH_SHORT).show()
                }
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error loading products: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun setupFab() {
        binding.fabAddProduct.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

}
