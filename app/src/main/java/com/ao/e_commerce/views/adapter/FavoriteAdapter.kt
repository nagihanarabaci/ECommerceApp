package com.ao.e_commerce.views.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ao.e_commerce.databinding.FavoriteCardBinding
import com.ao.e_commerce.models.Carts
import com.ao.e_commerce.models.Product
import com.ao.e_commerce.models.ProductCarts
import com.ao.e_commerce.utils.extensions.showSnackbar
import com.ao.e_commerce.viewmodels.FavoriteViewModel
import com.bumptech.glide.Glide

class FavoriteAdapter(private var favoriteList: List<Product>, private var viewModel: FavoriteViewModel) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    inner class FavoriteViewHolder(var design: FavoriteCardBinding) :
        RecyclerView.ViewHolder(design.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            FavoriteCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val list = favoriteList[position]
        val design = holder.design
        with(design) {
            textViewFavoriteProductName.text = list.title
            "${list.price} $".also { textViewFavoriteProductPrice.text = it }
            textViewFavoriteBrandName.text = list.brand
            Glide.with(root.context).load(list.thumbnail).into(imageViewFavoriteProduct)
            favoriteDeleteButton.setOnClickListener {
                root.showSnackbar("Do you want to remove this product from favorites?"){
                    viewModel.removeFavorite(list,root.context)
                    notifyItemRemoved(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

}