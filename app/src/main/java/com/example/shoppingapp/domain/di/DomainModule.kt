package com.example.shoppingapp.domain.di

import com.example.shoppingapp.domain.usecase.GetProductList
import com.example.shoppingapp.domain.usecase.impl.GetProductListImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindGetProductList(
        getProductListImpl: GetProductListImpl
    ): GetProductList
}