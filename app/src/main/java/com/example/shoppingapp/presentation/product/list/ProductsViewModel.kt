package com.example.shoppingapp.presentation.product.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.core.di.MainDispatcher
import com.example.shoppingapp.domain.usecase.GetProductList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductList: GetProductList,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableLiveData<ProductsUiState>()
    val uiState: LiveData<ProductsUiState>
        get() = _uiState

    fun onCreate() {
        viewModelScope.launch(dispatcher) {

            _uiState.postValue(
                ProductsUiState(isLoading = true, isError = false, productsItems = listOf())
            )

            val result = getProductList.invoke()

            result.fold(
                onSuccess = {
                    _uiState.postValue(
                        ProductsUiState(isLoading = false, productsItems = it)
                    )
                },
                onFailure = {
                    _uiState.postValue(
                        ProductsUiState(isLoading = false, isError = true)
                    )
                }
            )
        }
    }
}