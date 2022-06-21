package com.example.shoppingapp.domain.usecase.impl

import com.example.shoppingapp.domain.model.ProductItem
import com.example.shoppingapp.domain.repository.ProductRepository
import com.example.shoppingapp.domain.usecase.GetProductList

class GetProductListImpl (
       private val productRepository: ProductRepository
): GetProductList{

    override suspend operator fun invoke(): List<ProductItem> {
        return listOf()
    }
}