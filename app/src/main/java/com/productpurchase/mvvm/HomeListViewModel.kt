package com.productpurchase.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.productpurchase.room.ProductItemEntity
import kotlinx.coroutines.launch

class HomeListViewModel(private val repository: ProductRepo) : ViewModel() {

    private val _searchResults = MutableLiveData<List<ProductItemEntity>>()
    val searchResults: LiveData<List<ProductItemEntity>> = _searchResults

    val allFoods: LiveData<List<ProductItemEntity>> = repository.allProducts

    fun insertProduct(item: ProductItemEntity) = viewModelScope.launch {
        repository.insertProduct(item)
    }
    suspend fun insertAndReturnId(item: ProductItemEntity): Long {
        return repository.insertProduct(item)
    }

    fun search(query: String) {
        viewModelScope.launch {
            repository.searchProductItems(query).observeForever { result ->
                _searchResults.postValue(result)
            }
        }
    }
}
