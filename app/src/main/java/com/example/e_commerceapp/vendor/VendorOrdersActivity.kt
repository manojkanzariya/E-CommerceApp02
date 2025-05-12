package com.example.e_commerceapp.vendor

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.DeliveryAddress
import com.example.e_commerceapp.data.model.Order
import com.example.e_commerceapp.data.model.OrderStatus
import com.example.e_commerceapp.databinding.ActivityVendorOrdersBinding
import com.example.e_commerceapp.vendor.adapter.VendorOrdersAdapter
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class VendorOrdersActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val ordersList = mutableListOf<Order>()
    private lateinit var adapter: VendorOrdersAdapter
    private lateinit var binding: ActivityVendorOrdersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendorOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerVendorOrders)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = VendorOrdersAdapter(ordersList) { orderId, newStatus ->
            updateOrderStatus(orderId, newStatus)
        }
        recyclerView.adapter = adapter

        setupToolbar()
        loadVendorOrders()
    }
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Orders"
        }
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun updateOrderStatus(orderId: String, newStatus: OrderStatus) {
        FirebaseFirestore.getInstance().collection("orders")
            .document(orderId)
            .update("status", newStatus.name)
            .addOnSuccessListener {
                Toast.makeText(this, "Order status updated to ${newStatus.displayName}", Toast.LENGTH_SHORT).show()
                loadVendorOrders() // reload orders to reflect updated status
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed: ${exception.message}", Toast.LENGTH_LONG).show()
                Log.e("VendorOrders", "Error loading orders", exception)
            }
    }

    private fun loadVendorOrders() {
        val vendorId = FirebaseAuth.getInstance().currentUser?.uid ?: run {
            Log.e("VendorOrders", "No vendor ID - user not logged in")
            return
        }

        Log.d("VendorOrders", "Loading orders for vendor: $vendorId")

        FirebaseFirestore.getInstance()
            .collection("orders")
            .whereEqualTo("vendorId", vendorId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                Log.d("VendorOrders", "Query returned ${result.size()} documents")
                ordersList.clear()
                for (doc in result) {
                    val productId = try {
                        val items = doc.get("items") as? List<Map<String, Any>>
                        items?.firstOrNull()?.get("productId") as? String ?: ""
                    } catch (e: Exception) {
                        Log.e("VendorOrders", "Failed to parse productId", e)
                        ""
                    }

                    val order = Order(
                        id = doc.id,
                        orderId = doc.id,
                        productId = productId,
                        userId = doc.getString("userId") ?: "",
                        vendorId = vendorId,
                        quantity = try {
                            val items = doc.get("items") as? List<Map<String, Any>>
                            (items?.firstOrNull()?.get("quantity") as? Long)?.toInt() ?: 1
                        } catch (e: Exception) {
                            1
                        },
                        status = OrderStatus.fromString(doc.getString("status") ?: "") ?: OrderStatus.PENDING,
                        total = doc.getDouble("total") ?: 0.0,
                        timestamp = doc.getTimestamp("timestamp") ?: Timestamp.now(),
                        paymentMethod = doc.getString("paymentMethod") ?: "",
                        paymentStatus = doc.getString("paymentStatus") ?: "",
                        deliveryAddress = doc.get("deliveryAddress")?.let { addressMap ->
                            if (addressMap is Map<*, *>) {
                                DeliveryAddress(
                                    name = addressMap["name"] as? String ?: "",
                                    phone = addressMap["phone"] as? String ?: "",
                                    city = addressMap["city"] as? String ?: "",
                                    address = addressMap["address"] as? String ?: "",
                                    pincode = addressMap["pincode"] as? String ?: ""
                                )
                            } else {
                                DeliveryAddress()
                            }
                        } ?: DeliveryAddress(),
                        estimatedDelivery = doc.getString("estimatedDelivery") ?: ""
                    )

                    Log.d("VendorOrders", "Loaded Order: ${order.id}, productId=${order.productId}")
                    ordersList.add(order)
                }

                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Successfully loaded vendor orders", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to load orders: ${exception.message}", Toast.LENGTH_SHORT).show()
                Log.e("VendorOrders", "Error loading orders", exception)
            }
    }

}
