package com.example.shoppingapp.domain.repository

import com.example.shoppingapp.domain.model.ProductItem

interface ProductRepository {
    suspend fun getProducts() : List<ProductItem>
}