package com.example.shoppingapp.data.database.impl

import com.example.shoppingapp.data.database.ProductsLocalDataSource
import com.example.shoppingapp.data.database.dao.ProductDao
import com.example.shoppingapp.data.mapper.toEntity
import com.example.shoppingapp.data.mapper.toModel
import com.example.shoppingapp.domain.model.ProductItem
import javax.inject.Inject


class ProductsLocalDataSourceImpl @Inject constructor(private val productDao: ProductDao):ProductsLocalDataSource {

    override suspend fun isEmpty(): Boolean {
        return productDao.getAllProducts().isEmpty()
    }

    override suspend fun getListProduct(): List<ProductItem> {
        return productDao.getAllProducts().map { it.toModel() }
    }

    override suspend fun save(products: List<ProductItem>) {
        val result = products.map { it.toEntity() }

        productDao.insertAll(result)
    }

    override suspend fun findById(productId: String): ProductItem? {
        val result = productDao.getProductById(productId)

        return result?.toModel()
    }

}