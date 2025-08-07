package com.productpurchase.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class ProductDataClass(
    val id: Int = 0,
    var name: String,
    var price: String,
    var description: String,
    var imageUrl: String,
    var qRCode: String,
)
