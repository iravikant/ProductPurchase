package com.productpurchase.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeListViewModelFactory(
    private val repository: ProductRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeListViewModel::class.java)) {
            return HomeListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
