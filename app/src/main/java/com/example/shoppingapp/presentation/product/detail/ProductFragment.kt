package com.example.shoppingapp.presentation.product.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.shoppingapp.databinding.FragmentProductBinding

class ProductFragment : Fragment() {
    private val args: ProductFragmentArgs by navArgs()

    // Initialize variables
    private lateinit var binding: FragmentProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=  FragmentProductBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.apply {
            binding.productTitle.text = title
            binding.productDescription.text = description

            Glide
                .with(this@ProductFragment)
                .load(imageUrl)
                .into(binding.productImage);
        }
    }

}