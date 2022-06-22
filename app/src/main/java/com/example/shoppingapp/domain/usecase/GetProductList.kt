package com.example.shoppingapp.domain.usecase

import com.example.shoppingapp.domain.model.ProductItem

interface GetProductList{
    suspend fun invoke():List<ProductItem>

}