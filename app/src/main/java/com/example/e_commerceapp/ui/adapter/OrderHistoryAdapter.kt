package com.example.e_commerceapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.Order
import com.example.e_commerceapp.databinding.ItemOrderHistoryBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class OrderHistoryAdapter(
    private var orders: List<Order>,
    private val onOrderClicked: (Order) -> Unit
) : RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(private val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "StringFormatMatches")
        fun bind(order: Order) {
            with(binding) {
                tvOrderId.text = root.context.getString(R.string.order_id_format, order.orderId)

                // Format Date (Ensure `order.date` is a Long timestamp)
                val formattedDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                    .format(Date(order.date))
                tvOrderDate.text = formattedDate

                // Format Total Price
                tvOrderTotal.text = NumberFormat.getCurrencyInstance().format(order.total)

                // Set Order Status
                chipStatus.text = order.status.displayName
                chipStatus.setTextColor(ContextCompat.getColor(root.context, order.status.colorRes))

                // Set Item Count
                tvItemCount.text = root.context.getString(R.string.item_count_format, order.items.size)

                // Handle both card and button clicks
                btnViewDetails.setOnClickListener { onOrderClicked(order) }
                root.setOnClickListener { onOrderClicked(order) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size

    fun submitList(newOrders: List<Order>) {
        orders = newOrders
        notifyDataSetChanged()
    }
}
