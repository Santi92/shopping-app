package com.example.shoppingapp.features.products
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

import com.example.shoppingapp.databinding.FragmentItemProductBinding
import com.example.shoppingapp.features.products.model.ProductItem


class ProductsRecyclerViewAdapter(
    private val values: List<ProductItem>,
    private var onItemClicked: ((product: ProductItem) -> Unit)
) : RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val product = values[position].apply {
            holder.idView.text = title
            holder.description.text = description

            Glide
                .with(holder.contentView.context)
                .load(imageUrl)
                .into(holder.imageView);
        }

        holder.contentView.setOnClickListener { onItemClicked.invoke(product) }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val description: TextView = binding.description
        val imageView: ImageView =  binding.itemImage
        val contentView: View = binding.productContent

        override fun toString(): String {
            return super.toString() + " '" + description.text + "'"
        }
    }

}
