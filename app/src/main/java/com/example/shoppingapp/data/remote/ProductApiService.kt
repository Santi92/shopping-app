package com.example.shoppingapp.data.remote


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET("products")
    suspend fun getAllQuotes(@Query("limit") limit : Int): Response<List<ProductDTO>>
}