package com.productpurchase.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_items")
data class ProductItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: String,
    val description: String,
    val imageUrl: String,
    val qrCode: String = ""
)
