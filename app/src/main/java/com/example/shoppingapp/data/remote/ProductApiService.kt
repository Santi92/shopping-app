package com.example.shoppingapp.data.remote


import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {

    @GET("products")
    suspend fun getProducts(@Query("limit") limit : Int): Result<List<ProductDTO>>
}