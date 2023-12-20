package com.ao.e_commerce.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ao.e_commerce.databinding.FragmentHomeBinding
import com.ao.e_commerce.databinding.HomeProductCardBinding
import com.ao.e_commerce.models.Product
import com.ao.e_commerce.utils.SharedPreferencesHelper
import com.ao.e_commerce.utils.extensions.showToast
import com.ao.e_commerce.views.fragments.BaseFragmentDirections
import com.ao.e_commerce.views.fragments.CategoriesFragmentDirections
import com.bumptech.glide.Glide

class HomeProductCardAdapter(private val products: List<Product>, private val isCategoriesRoot:Boolean = false) :
    RecyclerView.Adapter<HomeProductCardAdapter.HomeProductCardViewHolder>() {

    inner class HomeProductCardViewHolder(var design: HomeProductCardBinding) :
        RecyclerView.ViewHolder(design.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeProductCardViewHolder {
        return HomeProductCardViewHolder(
            HomeProductCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeProductCardViewHolder, position: Int) {
        val product = products[position]
        with(holder.design) {
            textViewHomeProductTitle.text = product.title
            textViewHomeProductBrand.text = product.brand
            "${product.price} $".also { textViewHomeProductCardPrice.text = it }
            homeProductCardAddCartButton.setOnClickListener {
                val sharedPreferences = SharedPreferencesHelper.getSharedPreferences(root.context)
                SharedPreferencesHelper.addToBasket(sharedPreferences, product.id.toString())
                root.showToast("${product.title} added to basket")
            }
            Glide.with(root.context).load(product.thumbnail)
                .into(imageViewHomeProduct)
            root.setOnClickListener {
                val action = if(isCategoriesRoot.not()) BaseFragmentDirections.baseToDetail(product) else CategoriesFragmentDirections.categoriesToProductDetail(product)
                root.findNavController().navigate(action)
            }
        }
    }


    override fun getItemCount(): Int {
        return products.size
    }
}