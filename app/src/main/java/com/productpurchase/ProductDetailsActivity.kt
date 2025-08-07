package com.productpurchase

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.productpurchase.databinding.ActivityProductDetailsBinding
import com.productpurchase.room.AppDatabase
import com.productpurchase.utills.base64ToBitmap
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity() {
    lateinit var b: ActivityProductDetailsBinding
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(b.root)
        context = this@ProductDetailsActivity

        b.back.setNavigationOnClickListener {
            onBackPressed()
        }

        val deepLinkId = intent?.data?.getQueryParameter("id")?.toIntOrNull()
        val intentId = intent.getIntExtra("id", 0)
        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")
        val description = intent.getStringExtra("description")
        val imageUrl = intent.getStringExtra("imageUrl")
        val qrCode = intent.getStringExtra("qrCode")

        if (deepLinkId != null) {
            fetchProductById(deepLinkId)
            return
        }

        b.productName.text = name
        b.productPrice.text = "₹$price"
        b.productDescription.text = description

        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.img_shirt)
            .into(b.ProductImage)

        val qrBitmap = base64ToBitmap(qrCode ?: "")
        if (qrBitmap != null) {
            b.qrImage.setImageBitmap(qrBitmap)
        } else {
            b.qrImage.setImageResource(R.drawable.noqr)
        }
    }

    private fun fetchProductById(id: Int) {
        lifecycleScope.launch {
            val dao = AppDatabase.getInstance(this@ProductDetailsActivity).productItemDao()
            val product = dao.getProductById(id)
            if (product != null) {
                b.productName.text = product.name
                b.productPrice.text = "₹${product.price}"
                b.productDescription.text = product.description

                Glide.with(this@ProductDetailsActivity)
                    .load(product.imageUrl)
                    .placeholder(R.drawable.img_shirt)
                    .into(b.ProductImage)

                val qrBitmap = base64ToBitmap(product.qrCode)
                if (qrBitmap != null) {
                    b.qrImage.setImageBitmap(qrBitmap)
                } else {
                    b.qrImage.setImageResource(R.drawable.noqr)
                }
            } else {
                Toast.makeText(this@ProductDetailsActivity, "Product not found", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
