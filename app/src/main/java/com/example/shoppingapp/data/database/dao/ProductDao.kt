package com.example.shoppingapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppingapp.data.database.entities.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM products ORDER BY title DESC")
    suspend fun getAllProducts():List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products:List<ProductEntity>)

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductById(productId: String):ProductEntity?

    @Query("DELETE FROM products ")
    suspend fun deleteAllProducts()
}