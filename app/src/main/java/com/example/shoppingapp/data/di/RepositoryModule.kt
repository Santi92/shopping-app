package com.example.shoppingapp.data.di

import com.example.shoppingapp.data.local.ContentFake
import com.example.shoppingapp.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindProductRepository(
        productRepositoryImpl: ContentFake
    ): ProductRepository
}