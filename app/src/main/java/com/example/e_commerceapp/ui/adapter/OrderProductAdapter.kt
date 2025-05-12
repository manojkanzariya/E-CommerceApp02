package com.example.e_commerceapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.databinding.ItemOrderProductBinding

class OrderProductAdapter(
    private val products: List<Product>) :
    RecyclerView.Adapter<OrderProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: ItemOrderProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemOrderProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.binding.tvProductName.text = product.name
        holder.binding.tvProductPrice.text = product.price.toString()
        holder.binding.tvProductQuantity.text = "Qty: ${product.quantity}"

        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .centerCrop()
            .into(holder.binding.ivProductImage)
    }

    override fun getItemCount(): Int = products.size
}
