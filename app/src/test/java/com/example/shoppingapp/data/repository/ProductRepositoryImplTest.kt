package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.database.ProductsLocalDataSource
import com.example.shoppingapp.data.mapper.toModel
import com.example.shoppingapp.data.remote.ProductApiService
import com.example.shoppingapp.data.remote.ProductDTO
import com.example.shoppingapp.domain.model.ProductItem
import com.example.shoppingapp.util.MainDispatcherRule
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductRepositoryImplTest {

    @RelaxedMockK
    private lateinit var productApiService: ProductApiService
    @RelaxedMockK
    private lateinit var productsLocalDataSource: ProductsLocalDataSource

    private lateinit var productRepositoryImpl: ProductRepositoryImpl

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val dispatcher = mainDispatcherRule.testDispatcher

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        productRepositoryImpl = ProductRepositoryImpl(
            productApiService,
            productsLocalDataSource,
            dispatcher,
        )
    }

    @Test
    fun `when call getProducts from api services should get a product list`() = runTest(dispatcher) {
        //Given
        val item = ProductDTO("any", "any", 0.0,"any", "any","any")
        val list = listOf(item)
        coEvery { productApiService.getProducts(any()) } returns Result.success(list)
        coEvery { productsLocalDataSource.isEmpty() } returns true

        //When
        val result = productRepositoryImpl.getProducts()
        val resultList = result.getOrNull()

        //Then
        assert(result.isSuccess)
        assert(resultList!! == list.map { it.toModel() })
        coVerify(exactly = 0)  { productsLocalDataSource.getListProduct()   }

    }

    @Test
    fun `when call getProducts from local storage should get a product list`() = runTest(dispatcher) {
        //Given
        val item = ProductItem("any", "any", "any","any")
        val list = listOf(item)
        coEvery { productsLocalDataSource.getListProduct() } returns list
        coEvery { productsLocalDataSource.isEmpty() } returns false

        //When
        val result = productRepositoryImpl.getProducts()
        val resultList = result.getOrNull()

        //Then
        assert(result.isSuccess)
        assert(resultList!! == list.map { it })
        coVerify(exactly = 0)  { productApiService.getProducts(any()) }

    }

    @Test
    fun `when call getProducts from api services should get a error`() = runTest(dispatcher) {
        //Given
        coEvery { productApiService.getProducts(any()) } returns  Result.failure(NullPointerException("Error occurred"))
        coEvery { productsLocalDataSource.isEmpty() } returns true

        //When
        val result = productRepositoryImpl.getProducts()
        val resultList = result.getOrNull()

        //Then
        assert(result.isFailure)
        assert(resultList == null)
    }

    @Test
    fun `when call getProducts from local storage should get a error`() = runTest(dispatcher) {
        //Given
        coEvery { productsLocalDataSource.getListProduct() } throws NullPointerException("Error occurred")
        coEvery { productsLocalDataSource.isEmpty() } returns false

        //When
        val result = productRepositoryImpl.getProducts()
        val resultList = result.getOrNull()

        //Then
        assert(result.isFailure)
        assert(resultList == null)
    }

}