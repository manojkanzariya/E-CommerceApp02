package com.example.e_commerceapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.databinding.ItemRelatedProductBinding

class RelatedProductsAdapter(
    private val onItemClick: (Product) -> Unit
) : ListAdapter<Product, RelatedProductsAdapter.RelatedProductViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedProductViewHolder {
        val binding = ItemRelatedProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RelatedProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RelatedProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun updateList(newProducts: List<Product>) {
        submitList(newProducts) // Proper way for ListAdapter
    }

    inner class RelatedProductViewHolder(private val binding: ItemRelatedProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.tvProductName.text = product.name
            binding.tvProductPrice.text = "$${"%.2f".format(product.price)}"

            Glide.with(itemView.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.ivProductImage)

            itemView.setOnClickListener { onItemClick(product) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}
