package com.example.shoppingapp.data.local

import com.example.shoppingapp.features.products.model.ProductItem
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace to repository.
 */
object ContentProvider {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<ProductItem> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, ProductItem> = HashMap()

    private const val COUNT = 25

    private const val IMAGEN_URL = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createPlaceholderItem(i))
        }
    }

    private fun addItem(item: ProductItem) {
        ITEMS.add(item)
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