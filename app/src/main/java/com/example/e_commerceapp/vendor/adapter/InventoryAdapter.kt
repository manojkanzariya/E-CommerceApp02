package com.example.e_commerceapp.vendor.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.model.Product
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class InventoryAdapter(
    private val productList: List<Product>,
    private val onDelete: (Product) -> Unit,
    private val onEdit: (Product) -> Unit
) : RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {

    inner class InventoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvProductName)
        val price: TextView = view.findViewById(R.id.tvProductPrice)
        val image: ImageView = view.findViewById(R.id.ivProductImage)
        val btnDelete: MaterialButton = view.findViewById(R.id.btnDelete)
        val btnEdit: MaterialButton = view.findViewById(R.id.btnEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inventory_product, parent, false)
        return InventoryViewHolder(view)
    }

    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val product = productList[position]

        holder.name.text = product.name ?: ""
        holder.price.text = holder.itemView.context.getString(R.string.price_format, product.price ?: 0)

        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .into(holder.image)

        holder.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("Delete Product")
                .setMessage("Are you sure you want to delete this product?")
                .setPositiveButton("Delete") { _, _ ->
                    onDelete(product)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        holder.btnEdit.setOnClickListener { onEdit(product) }
    }

    override fun getItemCount() = productList.size
}