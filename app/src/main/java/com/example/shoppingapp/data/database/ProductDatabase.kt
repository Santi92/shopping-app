package com.example.shoppingapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppingapp.data.database.dao.ProductDao
import com.example.shoppingapp.data.database.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun getProductDao(): ProductDao
}