package com.ao.e_commerce.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ao.e_commerce.data.repository.ProductsRepository
import com.ao.e_commerce.models.Product
import com.ao.e_commerce.utils.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(var productViewModel: ProductsViewModel) : ViewModel() {
    val favoriteList = MutableLiveData<MutableList<Product>>()

    init {
        favoriteList.value = mutableListOf()
    }

    fun addFavorite(product: Product, context: Context) {
        val sharedPreferences = SharedPreferencesHelper.getSharedPreferences(context)
        val getFavorites = SharedPreferencesHelper.getFavorites(sharedPreferences)
        if (getFavorites != null && !getFavorites.contains("${product.id}"))
            SharedPreferencesHelper.putFavorites(sharedPreferences, "${getFavorites}${product.id},")
        favoriteList.value?.add(product)
        favoriteList.postValue(favoriteList.value)
    }

    fun removeFavorite(product: Product, context: Context) {
        val sharedPreferences = SharedPreferencesHelper.getSharedPreferences(context)
        val getFavorites = SharedPreferencesHelper.getFavorites(sharedPreferences)
        if (getFavorites != null && getFavorites.contains("${product.id}"))
            SharedPreferencesHelper.putFavorites(
                sharedPreferences,
                getFavorites.replace(
                    "${
                        if (getFavorites.split(",").isNotEmpty()) "," else ("")
                    }${product.id}", ""
                )
            )
        favoriteList.value?.remove(product)
        favoriteList.postValue(favoriteList.value)
    }

    fun getFavorite(context: Context) {
        viewModelScope.launch {
            productViewModel.getProducts()
            val products = productViewModel.productsList.value
            val sharedPreferences = SharedPreferencesHelper.getSharedPreferences(context)
            val getFavoritesStrings = SharedPreferencesHelper.getFavorites(sharedPreferences)
            val getFavoriteList = getFavoritesStrings?.split(",")
            products?.let {
                val tempFavoriteList = mutableListOf<Product>()
                for (product in it) {
                    if (getFavoriteList != null && getFavoriteList.contains("${product.id}")) {
                        tempFavoriteList.add(product)
                    }
                }
                favoriteList.postValue(tempFavoriteList)
            }
        }
    }
}