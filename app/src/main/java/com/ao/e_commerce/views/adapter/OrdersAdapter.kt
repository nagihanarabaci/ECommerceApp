package com.ao.e_commerce.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ao.e_commerce.databinding.FavoriteCardBinding
import com.ao.e_commerce.models.ProductCarts
import com.bumptech.glide.Glide

class OrdersAdapter(private val orders: List<ProductCarts>) : RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {
    inner class OrdersViewHolder(var design : FavoriteCardBinding) : RecyclerView.ViewHolder(design.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        var binding = FavoriteCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrdersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val list = orders[position]
        holder.design.textViewFavoriteProductName.text = list.title
        "${list.price} $".also { holder.design.textViewFavoriteProductPrice.text = it }
        "Quantity : ${list.quantity}".also { holder.design.textViewFavoriteBrandName.text = it }
        Glide.with(holder.design.root.context).load(list.thumbnail).into(holder.design.imageViewFavoriteProduct)
    }


    override fun getItemCount(): Int {
        return orders.size
    }

}