package com.example.shoppingapp.features.products

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingapp.data.local.ContentProvider
import com.example.shoppingapp.databinding.FragmentProductsListBinding

/**
 * A fragment representing a list of Items.
 */
class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsListBinding
    private val productsViewModel: ProductsViewModel by lazy { ProductsViewModel(ContentProvider()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=  FragmentProductsListBinding.inflate(inflater, container, false)

        with(binding.list) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = ProductsRecyclerViewAdapter(
                listOf(),
            ){ product ->
                val action = ProductsFragmentDirections.actionDetailProduct(
                    product.title,
                    product.description,
                    product.imageUrl,
                )

                this@ProductsFragment.findNavController().navigate(action)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productsViewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->

            binding.progressCircular.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE

            if(uiState.productsItems.isNotEmpty()){
                val adapter = binding.list.adapter

                if(adapter is ProductsRecyclerViewAdapter){
                    adapter.updateProducts(uiState.productsItems)
                }
                binding.list.visibility = View.VISIBLE
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LIST","onCreate");

        productsViewModel.onCreate()
    }


}