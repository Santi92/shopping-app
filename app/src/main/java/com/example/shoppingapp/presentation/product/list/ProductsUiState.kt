package com.example.shoppingapp.presentation.product.list

import com.example.shoppingapp.domain.model.ProductItem

data class ProductsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val productsItems: List<ProductItem> = listOf(),
)