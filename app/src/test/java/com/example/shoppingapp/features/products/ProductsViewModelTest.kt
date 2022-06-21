package com.example.shoppingapp.features.products

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shoppingapp.data.local.ContentProvider
import com.example.shoppingapp.features.products.model.ProductItem
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
class ProductsViewModelTest {

    @RelaxedMockK
    private lateinit var contentProvider: ContentProvider
    private lateinit var productsViewModel: ProductsViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        productsViewModel = ProductsViewModel(contentProvider)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when call onCreate should get a product list`() = runTest {
        //Given
        val item = ProductItem("any", "any", "any")
        val list = listOf(item)
        coEvery { contentProvider.getProducts() } returns list

        //When
        productsViewModel.onCreate()

        //Then
        assert(
            productsViewModel.uiState.value == ProductsUiState(
                isLoading = false,
                productsItems = list,
            )
        )
    }

    @Test
    fun `when call onCreate should get a product list empty`() = runTest {
        //Given
        val list = emptyList<ProductItem>()
        coEvery { contentProvider.getProducts() } returns list

        //When
        productsViewModel.onCreate()

        //Then
        assert(
            productsViewModel.uiState.value == ProductsUiState(
                isLoading = false,
                productsItems = list,
            )
        )
    }

    @Test
    fun `when call onCreate return a exception should show content error`() = runTest {
        //Given
        coEvery { contentProvider.getProducts() } throws NullPointerException("Error occurred")

        //When
        productsViewModel.onCreate()

        //Then
        assert(
            productsViewModel.uiState.value == ProductsUiState(
                isLoading = false,
                productsItems = emptyList(),
                isError = true,
            )
        )
    }
}