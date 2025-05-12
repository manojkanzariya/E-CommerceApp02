package com.example.e_commerceapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.databinding.ItemWishlistProductBinding

class WishlistAdapter(
    private val onProductClick: (String) -> Unit,
    private val onRemoveClick: (String) -> Unit
) : ListAdapter<Product, WishlistAdapter.WishlistViewHolder>(ProductDiffCallback()) {

    inner class WishlistViewHolder(private val binding: ItemWishlistProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                tvProductName.text = product.name
                tvProductPrice.text = "₹${product.price}"

                Glide.with(root.context)
                    .load(product.imageUrl)
                    .placeholder(R.drawable.ic_product_placeholder)
                    .into(ivProductImage)

                root.setOnClickListener { onProductClick(product.id) }
                btnRemove.setOnClickListener { onRemoveClick(product.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val binding = ItemWishlistProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WishlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}