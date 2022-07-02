package com.example.shoppingapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shoppingapp.data.mapper.toModel
import com.example.shoppingapp.data.remote.ProductApiService
import com.example.shoppingapp.data.remote.ProductDTO
import com.example.shoppingapp.domain.model.ProductItem
import com.example.shoppingapp.domain.usecase.GetProductList
import com.example.shoppingapp.presentation.product.list.ProductsUiState
import com.example.shoppingapp.presentation.product.list.ProductsViewModel
import com.example.shoppingapp.util.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description




@ExperimentalCoroutinesApi
class ProductRepositoryImplTest {

    @RelaxedMockK
    private lateinit var productApiService: ProductApiService

    private lateinit var productRepositoryImpl: ProductRepositoryImpl

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val dispatcher = mainDispatcherRule.testDispatcher

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        productRepositoryImpl = ProductRepositoryImpl(productApiService, UnconfinedTestDispatcher())
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }


    @Test
    fun `when call onCreate should get a product list`() = runTest(dispatcher) {
        //Given
        val item = ProductDTO("any", "any", 0.0,"any", "any","any")
        val list = listOf(item)
        coEvery { productApiService.getAllQuotes(any()) } returns Result.success(list)

        //When
        val result = productRepositoryImpl.getProducts()

        advanceUntilIdle() // R

        val resultList = result.getOrNull()

        //Then
        assert(result.isSuccess)
        assert(resultList!! == list.map { it.toModel() })
    }


}