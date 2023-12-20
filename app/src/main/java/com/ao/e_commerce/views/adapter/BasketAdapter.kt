package com.ao.e_commerce.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ao.e_commerce.databinding.BasketCardBinding
import com.ao.e_commerce.models.Product
import com.ao.e_commerce.utils.SharedPreferencesHelper
import com.ao.e_commerce.viewmodels.BasketViewModel
import com.bumptech.glide.Glide

class BasketAdapter(private var viewModel: BasketViewModel) :
    RecyclerView.Adapter<BasketAdapter.CartViewHolder>() {
    inner class CartViewHolder(var design: BasketCardBinding) : RecyclerView.ViewHolder(design.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = BasketCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    private fun updateCount(productId: String, productCount: Int, context: Context, position: Int) {
        val sharedPref = SharedPreferencesHelper
        val sharedPreferences = sharedPref.getSharedPreferences(context)
        SharedPreferencesHelper.addToBasket(sharedPreferences, productId, productCount.toString())
        viewModel.cartsList.value?.get(position)?.let { value ->
            val product = value.first
            if (product.id.toString() == productId) {
                viewModel.updateProduct(product, productCount)
                notifyItemChanged(position)
            }
        }
    }

    private fun deleteProduct(product: Product, context: Context, position: Int) {
        val sharedPref = SharedPreferencesHelper
        val sharedPreferences = sharedPref.getSharedPreferences(context)
        SharedPreferencesHelper.deleteFromBasket(sharedPreferences, product.id.toString())
        viewModel.removeProduct(product)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartList = viewModel.cartsList.value!!
        val (product, count) = cartList[position].first to cartList[position].second
        Glide.with(holder.design.root.context).load(product.thumbnail)
            .into(holder.design.imageViewBagProduct)
        holder.design.cartProductName.text = product.title
        holder.design.cartBrandName.text = product.brand
        if (count > 1) {
            "${product.price * count} $".also { holder.design.cartPrice.text = it }
            holder.design.cartCount.text = count.toString()
        } else {
            "${product.price} $".also { holder.design.cartPrice.text = it }
            holder.design.cartCount.text = count.toString()
        }

        holder.design.cartDeleteButton.setOnClickListener {
            deleteProduct(product, holder.design.root.context, position)
        }

        holder.design.cartIncreaseButton.setOnClickListener {
            val newCount = count + 1
            "${product.price * newCount} $".also { holder.design.cartPrice.text = it }
            updateCount(product.id.toString(), 1, holder.design.root.context, position)
        }

        holder.design.cartDecreaseButton.setOnClickListener {
            if (count > 1) {
                val newCount = count - 1
                "${product.price * newCount} $".also {
                    holder.design.cartPrice.text = it
                }
                holder.design.cartCount.text = newCount.toString()
                updateCount(product.id.toString(), -1, holder.design.root.context, position)
            }
        }


    }

    override fun getItemCount(): Int {
        return viewModel.cartsList.value!!.size
    }
}

