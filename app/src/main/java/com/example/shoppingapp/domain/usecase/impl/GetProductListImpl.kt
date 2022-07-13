package com.example.shoppingapp.domain.usecase.impl

import com.example.shoppingapp.domain.model.ProductItem
import com.example.shoppingapp.domain.repository.ProductRepository
import com.example.shoppingapp.domain.usecase.GetProductList
import javax.inject.Inject

class GetProductListImpl @Inject constructor(
   private val productRepository: ProductRepository
): GetProductList{

    override suspend operator fun invoke(): Result<List<ProductItem>> {
        return productRepository.getProducts()
    }
}