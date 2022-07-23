package com.example.shoppingapp.data.database.impl

import com.example.shoppingapp.data.database.dao.ProductDao
import com.example.shoppingapp.data.database.entities.ProductEntity
import com.example.shoppingapp.data.mapper.toModel
import com.example.shoppingapp.domain.model.ProductItem
import com.example.shoppingapp.util.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductsLocalDataSourceImplTest {

    @RelaxedMockK
    private lateinit var productDao: ProductDao

    private lateinit var productsLocalDataSourceImpl: ProductsLocalDataSourceImpl

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val dispatcher = mainDispatcherRule.testDispatcher

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        productsLocalDataSourceImpl = ProductsLocalDataSourceImpl(productDao)
    }

    @Test
    fun `when call isEmpty and database is empty`() = runTest(dispatcher) {
        coEvery { productDao.getAllProducts() } returns emptyList()

        val result = productsLocalDataSourceImpl.isEmpty()
        assert(result)
    }

    @Test
    fun `when call isEmpty and database is no empty`() = runTest(dispatcher) {
        coEvery { productDao.getAllProducts() } returns listOf(ProductEntity("any","any", "any","any"))

        val result = productsLocalDataSourceImpl.isEmpty()
        assert(!result)
    }

    @Test
    fun `when call getListProduct and database is no empty`() = runTest(dispatcher) {
        val productsEntity = listOf(ProductEntity("any","any", "any","any"))
        val productsModel = productsEntity.map { it.toModel() }
        coEvery { productDao.getAllProducts() } returns productsEntity

        val result = productsLocalDataSourceImpl.getListProduct()
        assert(result == productsModel)
    }

    @Test
    fun `when call getListProduct and database is empty`() = runTest(dispatcher) {
        coEvery { productDao.getAllProducts() } returns emptyList()

        val result = productsLocalDataSourceImpl.getListProduct()
        assert(result.isEmpty())
    }

    @Test
    fun `when save products`() = runTest(dispatcher) {
        coEvery { productDao.insertAll(any()) } returns Unit

        val products = listOf(ProductItem("productId", "title","imageUrl", "description"))
        productsLocalDataSourceImpl.save(products)

        coVerify(exactly = 1)  { productDao.insertAll(any())  }
    }


    @Test
    fun `when call findById and when find a product`() = runTest(dispatcher) {
        val productEntity = ProductEntity("any","any", "any","any")
        val productModel = productEntity.toModel()

        coEvery { productDao.getProductById(productEntity.id) } returns productEntity

        val result = productsLocalDataSourceImpl.findById(productEntity.id)

        assert(result == productModel)
    }

    @Test
    fun `when call findById and when can't find a product`() = runTest(dispatcher) {
        coEvery { productDao.getProductById(any()) } returns null

        val result = productsLocalDataSourceImpl.findById("productId")

        assert(result == null)
    }


}