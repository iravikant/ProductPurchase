package com.productpurchase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.productpurchase.adapter.ProductListAdapter
import com.productpurchase.databinding.ActivityDashboardBinding
import com.productpurchase.mvvm.HomeListViewModel
import com.productpurchase.mvvm.HomeListViewModelFactory
import com.productpurchase.mvvm.ProductRepo
import com.productpurchase.room.AppDatabase
import com.productpurchase.room.ProductItemEntity

class DashboardActivity : AppCompatActivity() {

    private lateinit var b: ActivityDashboardBinding
    private lateinit var context: Context
    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var homeListViewModel: HomeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(b.root)
        context = this

        b.uploadProduct.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }

        val database = AppDatabase.getInstance(this)
        val productRepo = ProductRepo(database.productItemDao())
        homeListViewModel = ViewModelProvider(
            this,
            HomeListViewModelFactory(productRepo)
        )[HomeListViewModel::class.java]

        productListAdapter = ProductListAdapter(emptyList(), context)
        b.recProduct.adapter = productListAdapter

        homeListViewModel.allFoods.observe(this) { productList: List<ProductItemEntity> ->
            productListAdapter.updateList(productList)
        }

        homeListViewModel.searchResults.observe(this) { result: List<ProductItemEntity> ->
            productListAdapter.updateList(result)
        }

        b.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                if (query.isNotEmpty()) {
                    homeListViewModel.search(query)
                } else {
                    homeListViewModel.allFoods.value?.let {
                        productListAdapter.updateList(it)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
