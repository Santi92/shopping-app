package com.example.shoppingapp.presentation.product.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingapp.databinding.FragmentProductsListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsListBinding
    private val productsViewModel: ProductsViewModel by viewModels()


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

            binding.contentError.visibility  = if (uiState.isError) View.VISIBLE else View.GONE

            if(uiState.productsItems.isNotEmpty() && !uiState.isError ){
                val adapter = binding.list.adapter

                if(adapter is ProductsRecyclerViewAdapter){
                    adapter.updateProducts(uiState.productsItems)
                }
                binding.list.visibility = View.VISIBLE
            }
        })
        setupListener()
    }

    private fun setupListener() {
        binding.contentError.setOnClickListener {
            productsViewModel.onCreate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LIST","onCreate");

        productsViewModel.onCreate()
    }


}