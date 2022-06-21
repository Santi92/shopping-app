package com.example.shoppingapp.presentation.product.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.local.ContentProvider
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val contentProvider: ContentProvider
    ) :ViewModel() {

    private val _uiState = MutableLiveData<ProductsUiState>()
    val uiState : LiveData<ProductsUiState>
        get() = _uiState

    fun onCreate(){
        viewModelScope.launch {

            _uiState.postValue(
                ProductsUiState(isLoading = true, isError = false, productsItems= listOf())
            )

            try {
                val result =  contentProvider.getProducts()
                _uiState.postValue(
                    ProductsUiState(isLoading = false, productsItems = result)
                )
            } catch (e: Exception) {
                println("Caught exception $e")

                _uiState.postValue(
                    ProductsUiState(isLoading = false, isError = true)
                )
            }


        }
    }
}