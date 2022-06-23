package com.example.shoppingapp.data.mapper

import com.example.shoppingapp.data.remote.ProductDTO
import com.example.shoppingapp.domain.model.ProductItem

fun ProductDTO.toModel(): ProductItem {

    return ProductItem(
        title ?:"",
        image ?:"",
        description ?:"",
    )
}