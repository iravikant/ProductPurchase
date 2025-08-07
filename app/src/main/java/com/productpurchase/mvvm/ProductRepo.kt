package com.productpurchase.mvvm

import androidx.lifecycle.LiveData
import com.productpurchase.room.ProductItemDao
import com.productpurchase.room.ProductItemEntity

class ProductRepo(private val productItemDao: ProductItemDao) {

    val allProducts: LiveData<List<ProductItemEntity>> = productItemDao.getAllProduct()

    suspend fun insertProduct(item: ProductItemEntity): Long = productItemDao.insertProduct(item)

    fun searchProductItems(query: String): LiveData<List<ProductItemEntity>> {
        return productItemDao.searchProductItems(query)
    }
}
