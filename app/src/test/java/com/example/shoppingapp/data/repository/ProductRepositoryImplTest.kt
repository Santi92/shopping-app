package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.mapper.toModel
import com.example.shoppingapp.data.remote.ProductApiService
import com.example.shoppingapp.data.remote.ProductDTO
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
    }

    @Test
    fun `when call getAllQuotes should get a product list`() = runTest(dispatcher) {
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

    @Test
    fun `when call getAllQuotes should get a error`() = runTest(dispatcher) {
        //Given
        val item = ProductDTO("any", "any", 0.0,"any", "any","any")
        coEvery { productApiService.getAllQuotes(any()) } returns  Result.failure(NullPointerException("Error occurred"))

        //When
        val result = productRepositoryImpl.getProducts()

        advanceUntilIdle() // R

        val resultList = result.getOrNull()

        //Then
        assert(result.isFailure)
        assert(resultList == null)
    }

}