package com.productpurchase.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(item: ProductItemEntity): Long

    @Query("SELECT * FROM product_items")
    fun getAllProduct(): LiveData<List<ProductItemEntity>>

    @Query("SELECT * FROM product_items WHERE name LIKE '%' || :searchQuery || '%'")
    fun searchProductItems(searchQuery: String): LiveData<List<ProductItemEntity>>

    @Query("SELECT * FROM product_items WHERE id = :id")
    suspend fun getProductById(id: Int): ProductItemEntity?
}

