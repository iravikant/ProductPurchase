package com.productpurchase

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.productpurchase.databinding.ActivityAddProductBinding
import com.productpurchase.mvvm.HomeListViewModel
import com.productpurchase.mvvm.HomeListViewModelFactory
import com.productpurchase.mvvm.ProductRepo
import com.productpurchase.room.AppDatabase
import com.productpurchase.room.ProductItemEntity
import com.productpurchase.utills.bitmapToBase64
import com.productpurchase.utills.generateQRCode
import kotlinx.coroutines.launch


class AddProductActivity : AppCompatActivity() {
    private lateinit var b: ActivityAddProductBinding
    private lateinit var context: Context
    private lateinit var homeListViewModel: HomeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(b.root)
        context = this@AddProductActivity
        b.back.setNavigationOnClickListener {
            onBackPressed()
        }

        val dao = AppDatabase.getInstance(this).productItemDao()
        val repo = ProductRepo(dao)
        homeListViewModel =
            ViewModelProvider(this, HomeListViewModelFactory(repo))[HomeListViewModel::class.java]

        b.btnSave.setOnClickListener {
            val name = b.etProductName.text.toString().trim()
            val price = b.etPrice.text.toString().trim()
            val description = b.etDescription.text.toString().trim()
            val imageUrl = b.etImageUrl.text.toString().trim()

            if (name.isEmpty() || price.isEmpty() || description.isEmpty() || imageUrl.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val product = ProductItemEntity(
                name = name,
                price = price,
                description = description,
                imageUrl = imageUrl
            )

            lifecycleScope.launch {
                val insertedId = homeListViewModel.insertAndReturnId(product)

                val deepLink = "productapp://product?id=$insertedId"
                val qrBitmap = generateQRCode(deepLink)
                val qrBase64 = bitmapToBase64(qrBitmap)

                val updatedProduct = product.copy(
                    id = insertedId.toInt(),
                    qrCode = qrBase64
                )

                homeListViewModel.insertProduct(updatedProduct)

                Toast.makeText(this@AddProductActivity, "Product added", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}