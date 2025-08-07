package com.productpurchase.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.productpurchase.ProductDetailsActivity
import com.productpurchase.R
import com.productpurchase.databinding.ProductsItemBinding
import com.productpurchase.room.ProductItemEntity

class ProductListAdapter(private var list: List<ProductItemEntity>, val context: Context) :
    RecyclerView.Adapter<ProductListAdapter.MyViewHolder>() {

    fun updateList(newList: List<ProductItemEntity>) {
        list = newList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.products_item, parent, false)
        return MyViewHolder(
            ProductsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )

        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.b.productName.text = list.get(position).name
        holder.b.productDescription.text = list.get(position).description
        holder.b.productPrice.text = list.get(position).price


        Glide.with(context)
            .load(list.get(position).imageUrl)
            .placeholder(R.drawable.img_shirt)
            .into(holder.b.productImage)


            holder.b.root.setOnClickListener {
                val intent = Intent(context, ProductDetailsActivity::class.java)
                intent.data = Uri.parse("productapp://product?id=${list[position].id}")
                context.startActivity(intent)

            }

        }

        override fun getItemCount(): Int {
            return list.size
        }

        class MyViewHolder(b: ProductsItemBinding) : RecyclerView.ViewHolder(b.root) {
            public val b: ProductsItemBinding

            init {
                this.b = b
            }
        }
    }
