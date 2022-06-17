package com.example.shoppingapp.features.products

import com.example.shoppingapp.features.products.model.ProductItem

data class ProductsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val productsItems: List<ProductItem> = listOf(),
)