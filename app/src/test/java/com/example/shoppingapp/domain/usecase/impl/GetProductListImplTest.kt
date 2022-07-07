package com.example.shoppingapp.domain.usecase.impl

import com.example.shoppingapp.domain.model.ProductItem
import com.example.shoppingapp.domain.repository.ProductRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetProductListImplTest{

    @RelaxedMockK
    private lateinit var productRepository: ProductRepository

    private lateinit var getProductListImpl: GetProductListImpl

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getProductListImpl = GetProductListImpl(productRepository)
    }

    @Test
    fun `when call invoke get products should get a product list`() = runTest {
        //Given
        val item = ProductItem("any", "any", "any")
        val list = listOf(item)
        coEvery { productRepository.getProducts() } returns Result.success(list)

        //When
        val result = getProductListImpl.invoke()
        val resultList = result.getOrNull()

        //Then
        assert(result.isSuccess)
        assert(resultList!! == list )
    }

    @Test
    fun `when invoke get products should get a error`() = runTest {
        //Given
        coEvery { productRepository.getProducts() } returns  Result.failure(NullPointerException("Error occurred"))

        //When
        val result = getProductListImpl.invoke()
        val resultList = result.getOrNull()

        //Then
        assert(result.isFailure)
        assert(resultList == null)
    }

}