package com.example.e_commerceapp.vendor.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.Order
import com.example.e_commerceapp.data.model.OrderStatus
import com.example.e_commerceapp.databinding.ItemVendorOrderBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class VendorOrdersAdapter(
    private val orders: List<Order>,
    private val onStatusUpdate: (String, OrderStatus) -> Unit
) : RecyclerView.Adapter<VendorOrdersAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(private val binding: ItemVendorOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvOrderId = binding.tvOrderId
        val tvOrderDate = binding.tvOrderDate
        val ivProductImage = binding.ivProductImage
        val tvProductName = binding.tvProductName
        val tvProductPrice = binding.tvProductPrice
        val tvQuantity = binding.tvQuantity
        val tvCustomerName = binding.tvCustomerName
        val tvCustomerPhone = binding.tvCustomerPhone
        val tvStatus = binding.tvStatus
        val tvOrderTotal = binding.tvOrderTotal
        val btnUpdateStatus = binding.btnUpdateStatus
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemVendorOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        val context = holder.itemView.context

        // Use binding instead of findViewById
        holder.tvOrderId.text = context.getString(R.string.order_id_format, order.id.take(8))
        holder.tvOrderDate.text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            .format(order.timestamp.toDate())

        holder.tvQuantity.text = "x${order.quantity ?: 1}"
        holder.tvOrderTotal.text = context.getString(R.string.price_format, order.total ?: 0.0)

        val status = try {
            OrderStatus.valueOf((order.status ?: "PENDING").toString())
        } catch (e: IllegalArgumentException) {
            OrderStatus.PENDING
        }

        holder.tvStatus.text = status.displayName
        when (status) {
            OrderStatus.PENDING -> holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_pending))
            OrderStatus.PROCESSING -> holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_processing))
            OrderStatus.SHIPPED -> holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_shipped))
            OrderStatus.DELIVERED -> holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_delivered))
            OrderStatus.CANCELLED -> holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_cancelled))
        }

        // Load product info
        FirebaseFirestore.getInstance().collection("products")
            .document(order.productId)
            .get()
            .addOnSuccessListener { document ->
                document.getString("name")?.let { holder.tvProductName.text = it }
                document.getDouble("price")?.let { price ->
                    holder.tvProductPrice.text = context.getString(R.string.price_format, price)
                }
                document.getString("imageUrl")?.let { imageUrl ->
                    Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_error)
                        .into(holder.ivProductImage)
                }
            }

        // Load customer info
        FirebaseFirestore.getInstance().collection("users")
            .document(order.userId)
            .get()
            .addOnSuccessListener { document ->
                holder.tvCustomerName.text = document.getString("name") ?: "Unknown Customer"
                holder.tvCustomerPhone.text = document.getString("phone") ?: "No phone provided"
            }

        // Set up status update button
        holder.btnUpdateStatus.setOnClickListener {
            showStatusUpdateDialog(holder.itemView.context, order) { orderId, newStatus ->
                updateOrderStatus(orderId, newStatus, holder.tvStatus)
            }
        }
    }

    private fun updateOrderStatus(orderId: String, newStatus: OrderStatus, statusTextView: TextView) {
        FirebaseFirestore.getInstance().collection("orders")
            .document(orderId)
            .update("status", newStatus.name)
            .addOnSuccessListener {
                statusTextView.text = "Status: ${newStatus.displayName}"
                Toast.makeText(statusTextView.context, "Order updated to ${newStatus.displayName}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount() = orders.size

    private fun showStatusUpdateDialog(context: Context, order: Order, onStatusUpdate: (String, OrderStatus) -> Unit) {
        val currentStatusEnum = OrderStatus.fromString(order.status.toString()) ?: return

        val statuses = OrderStatus.values()
            .filter { it.ordinal > currentStatusEnum.ordinal }

        if (statuses.isEmpty()) return

        val builder = AlertDialog.Builder(context)
            .setTitle("Update Order Status")
            .setItems(statuses.map { it.displayName }.toTypedArray()) { _, which ->
                val selectedStatus = statuses[which]
                onStatusUpdate(order.id, selectedStatus)
            }
            .setNegativeButton("Cancel", null)

        builder.create().show()
    }

}