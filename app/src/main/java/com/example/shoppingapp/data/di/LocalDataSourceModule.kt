package com.example.shoppingapp.data.di

import com.example.shoppingapp.data.database.ProductsLocalDataSource
import com.example.shoppingapp.data.database.impl.ProductsLocalDataSourceImpl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Singleton
    @Binds
    abstract fun bindProductsLocalDataSource(
        productsLocalDataSourceImpl: ProductsLocalDataSourceImpl
    ): ProductsLocalDataSource
}

