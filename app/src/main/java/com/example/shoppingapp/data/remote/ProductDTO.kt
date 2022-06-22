package com.example.shoppingapp.data.remote

data class ProductDTO(
    val id: String,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
)
