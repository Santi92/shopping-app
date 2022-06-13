package com.example.shoppingapp.features.products

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

import com.example.shoppingapp.databinding.FragmentItemProductBinding
import com.example.shoppingapp.features.products.model.ProductItem


class MyProductsRecyclerViewAdapter(
    private val values: List<ProductItem>,
    private val context: ProductsFragment,
) : RecyclerView.Adapter<MyProductsRecyclerViewAdapter.ViewHolder>() {

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
        values[position].run {
            holder.idView.text = title
            holder.contentView.text = description

            Glide
                .with(context)
                .load(imageUrl)

                .into(holder.imageView);
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
        val imageView: ImageView =  binding.itemImage

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}
