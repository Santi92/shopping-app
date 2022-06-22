package com.example.shoppingapp.data.local

import com.example.shoppingapp.core.di.IoDispatcher
import com.example.shoppingapp.domain.model.ProductItem
import com.example.shoppingapp.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject

/**
 * TODO: Replace to repository.
 */
class ContentFake @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ProductRepository {

    /**
     * An array of sample (placeholder) items.
     */
    val itemsLocal: MutableList<ProductItem> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, ProductItem> = HashMap()

    private val COUNT = 25

    private val IMAGEN_URL = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"


    override suspend fun getProducts(): List<ProductItem> {

        return withContext(dispatcher) {
            itemsLocal.clear()

            delay(5000L)
            for (i in 1..COUNT) {
                addItem(createPlaceholderItem(i))
            }

            return@withContext itemsLocal
        }

    }

    private fun addItem(item: ProductItem) {
        itemsLocal.add(item)
        ITEM_MAP.put(item.title, item)
    }

    private fun createPlaceholderItem(position: Int): ProductItem {
        return ProductItem(position.toString(), IMAGEN_URL, makeDetails(position))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        return builder.toString()
    }


}