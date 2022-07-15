package com.example.shoppingapp.data.database

import com.example.shoppingapp.domain.model.ProductItem

interface ProductsLocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun getListProduct(): List<ProductItem>
    suspend fun save(products: List<ProductItem>)
    suspend fun findById(productId:String):ProductItem
}