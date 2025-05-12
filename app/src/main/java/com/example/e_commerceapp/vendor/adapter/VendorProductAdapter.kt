package com.example.e_commerceapp.vendor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.databinding.ItemVendorProductBinding

class VendorProductAdapter(
    private val productList: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<VendorProductAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemVendorProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVendorProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = "₹${product.price}"
            
            Glide.with(holder.itemView.context)
                .load(product.imageUrl)
                .placeholder(com.example.e_commerceapp.R.drawable.ic_placeholder)
                .into(holder.binding.ivProductImage)
            root.setOnClickListener { onItemClick(product) }
        }
    }

    override fun getItemCount() = productList.size
}