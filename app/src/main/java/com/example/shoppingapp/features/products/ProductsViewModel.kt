package com.example.shoppingapp.features.products

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
                ProductsUiState(true, listOf())
            )

            val result =  contentProvider.getProducts()

            _uiState.postValue(
                ProductsUiState(false, result)
            )
        }
    }
}