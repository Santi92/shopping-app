package com.example.shoppingapp.data.mapper

import com.example.shoppingapp.data.database.entities.ProductEntity
import com.example.shoppingapp.data.remote.ProductDTO
import com.example.shoppingapp.domain.model.ProductItem

fun ProductDTO.toModel(): ProductItem {

    return ProductItem(
        id ?: "",
        title ?:"",
        image ?:"",
        description ?:"",
    )
}

fun ProductItem.toEntity(): ProductEntity {

    return ProductEntity(
        id = productId,
        title = title,
        description = description,
        image = imageUrl,
    )
}

fun ProductEntity.toModel(): ProductItem {

    return ProductItem(
        productId = id,
        title = title,
        description = description,
        imageUrl = image,
    )
}