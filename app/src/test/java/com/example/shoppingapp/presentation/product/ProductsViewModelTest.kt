package com.example.shoppingapp.presentation.product

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shoppingapp.presentation.product.list.ProductsUiState
import com.example.shoppingapp.presentation.product.list.ProductsViewModel
import com.example.shoppingapp.domain.model.ProductItem
import com.example.shoppingapp.domain.usecase.GetProductList
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
    private lateinit var getProductList: GetProductList
    private lateinit var productsViewModel: ProductsViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        productsViewModel = ProductsViewModel(getProductList, UnconfinedTestDispatcher())
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
        coEvery { getProductList.invoke() } returns Result.success(list)

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
        coEvery { getProductList.invoke() } returns Result.success(list)

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
        coEvery { getProductList.invoke() } returns  Result.failure(NullPointerException("Error occurred"))

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