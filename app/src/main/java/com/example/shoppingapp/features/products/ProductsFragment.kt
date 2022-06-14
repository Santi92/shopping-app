package com.example.shoppingapp.features.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.data.local.ContentProvider

/**
 * A fragment representing a list of Items.
 */
class ProductsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_products_list, container, false)
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = GridLayoutManager(context, 2)
                adapter = ProductsRecyclerViewAdapter(
                    ContentProvider.ITEMS,
                ){product ->
                    val action = ProductsFragmentDirections.actionDetailProduct(
                        product.title,
                        product.description,
                        product.imageUrl,
                    )

                    this@ProductsFragment.findNavController().navigate(action)
                }
            }
        }
        return view
    }


}