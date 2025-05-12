package com.example.e_commerceapp.vendor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.e_commerceapp.R
//import com.example.e_commerceapp.ui.auth.LoginActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.Calendar

class VendorDashboardActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var tvOrdersCount: TextView
    private lateinit var tvRevenue: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor_dashboard)

        // Initialize Firebase instances
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        // Setup toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Setup drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Load vendor data for navigation header
        loadVendorHeaderData(navView)

        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {}
                R.id.nav_products -> startActivity(Intent(this, VendorProductListActivity::class.java))
                R.id.nav_orders -> startActivity(Intent(this, VendorOrdersActivity::class.java))
                R.id.nav_inventory -> startActivity(Intent(this, InventoryActivity::class.java))
                R.id.nav_analytics -> startActivity(Intent(this, AnalyticsActivity::class.java))
                R.id.nav_settings -> startActivity(Intent(this, VendorSettingsActivity::class.java))
                /*R.id.nav_logout -> {
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }*/
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Bind summary views
        tvOrdersCount = findViewById(R.id.tvOrdersCount)
        tvRevenue = findViewById(R.id.tvRevenue)

        // Button actions
        findViewById<Button>(R.id.btnAddProduct).setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }
        findViewById<Button>(R.id.btnViewOrders).setOnClickListener {
            startActivity(Intent(this, VendorOrdersActivity::class.java))
        }
        findViewById<Button>(R.id.btnManageInventory).setOnClickListener {
            startActivity(Intent(this, InventoryActivity::class.java))
        }
        findViewById<Button>(R.id.btnViewAnalytics).setOnClickListener {
            startActivity(Intent(this, AnalyticsActivity::class.java))
        }

        loadDashboardData()
    }

    private fun loadVendorHeaderData(navView: NavigationView) {
        val currentUser = auth.currentUser
        val vendorId = currentUser?.uid ?: return

        // Get the header view
        val headerView = navView.getHeaderView(0)
        val tvStoreName = headerView.findViewById<TextView>(R.id.tvStoreName)
        val tvVendorEmail = headerView.findViewById<TextView>(R.id.tvVendorEmail)
        //val ivStoreImage = headerView.findViewById<ImageView>(R.id.ivStoreImage)

        // Set email from auth
        tvVendorEmail.text = currentUser.email ?: "No email"

        // Load vendor data from Firestore
        db.collection("vendors").document(vendorId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Set store name
                    val storeName = document.getString("storeName") ?: "My Store"
                    tvStoreName.text = storeName

                    // Load store image
                    document.getString("imageUrl")?.let { imageUrl ->
                        Glide.with(this)
                            .load(imageUrl)
                            .placeholder(R.drawable.ic_store)
                            .error(R.drawable.ic_store)
                            //.into(ivStoreImage)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Use default values if loading fails
                tvStoreName.text = "My Store"
                //ivStoreImage.setImageResource(R.drawable.ic_store)
            }
    }

    private fun loadDashboardData() {
        val vendorId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        // 1. Get start and end of today
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = Timestamp(calendar.time)

        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val endOfDay = Timestamp(calendar.time)

        // 2. Fetch today's orders count for current vendor
        db.collection("orders")
            .whereEqualTo("vendorId", vendorId)
            .whereGreaterThanOrEqualTo("timestamp", startOfDay)
            .whereLessThan("timestamp", endOfDay)
            .get()
            .addOnSuccessListener { result ->
                tvOrdersCount.text = result.size().toString()
            }
            .addOnFailureListener {
                tvOrdersCount.text = "0"
            }

        // 3. Fetch total revenue for current vendor
        db.collection("orders")
            .whereEqualTo("vendorId", vendorId)
            .get()
            .addOnSuccessListener { result ->
                var totalRevenue = 0.0
                for (doc in result) {
                    val price = doc.getDouble("total") ?: 0.0
                    totalRevenue += price
                }
                tvRevenue.text = "₹%.2f".format(totalRevenue)
            }
            .addOnFailureListener {
                tvRevenue.text = "₹0.00"
            }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}