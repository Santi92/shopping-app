package com.example.shoppingapp.domain.usecase

import com.example.shoppingapp.domain.model.ProductItem

interface GetProductList{
    suspend fun invoke():Result<List<ProductItem>>

}