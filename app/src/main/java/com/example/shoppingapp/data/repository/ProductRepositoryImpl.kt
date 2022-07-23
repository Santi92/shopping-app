package com.example.shoppingapp.data.repository

import com.example.shoppingapp.core.di.IoDispatcher
import com.example.shoppingapp.data.database.ProductsLocalDataSource
import com.example.shoppingapp.data.mapper.toModel

import com.example.shoppingapp.data.remote.ProductApiService
import com.example.shoppingapp.domain.model.ProductItem
import com.example.shoppingapp.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApiService: ProductApiService,
    private val productsLocalDataSource: ProductsLocalDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    ) : ProductRepository {

    override suspend fun getProducts(): Result<List<ProductItem>> {

        return withContext(dispatcher) {

            if (productsLocalDataSource.isEmpty()) {
                val result = productApiService.getProducts(15)

                result.map { items ->
                    items.map { it.toModel() }
                }.apply {

                    if(isSuccess){
                        productsLocalDataSource.save(this.getOrThrow())
                    }

                }

            }else{

                Result.success(
                    productsLocalDataSource.getListProduct()
                )
            }

        }
    }



}