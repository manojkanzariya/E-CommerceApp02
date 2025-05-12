package com.example.e_commerceapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.CartItem
import com.example.e_commerceapp.databinding.ItemCartProductBinding

class CartAdapter(
    private val items: List<CartItem>,
    private val onUpdate: () -> Unit,
    private val onItemRemoved: (CartItem) -> Unit,
    private val onQuantityChanged: (CartItem, Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem) {
            val product = cartItem.product
            binding.tvProductName.text = product.name
            binding.tvProductPrice.text = "$${product.price}"
            binding.tvQuantity.text = cartItem.quantity.toString()

            Glide.with(binding.root.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.ivProductImage)

            // Increase Quantity
            binding.btnIncrease.setOnClickListener {
                val newQuantity = cartItem.quantity + 1
                onQuantityChanged(cartItem, newQuantity)
                onUpdate()
            }

            // Decrease Quantity
            binding.btnDecrease.setOnClickListener {
                if (cartItem.quantity > 1) {
                    val newQuantity = cartItem.quantity - 1
                    onQuantityChanged(cartItem, newQuantity)
                    onUpdate()
                }
            }

            // Remove Item
            binding.btnRemove.setOnClickListener {
                onItemRemoved(cartItem)
                onUpdate()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}