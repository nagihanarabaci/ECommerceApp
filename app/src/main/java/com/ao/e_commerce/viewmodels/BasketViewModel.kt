package com.ao.e_commerce.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ao.e_commerce.models.Product

class BasketViewModel : ViewModel() {
    val cartsList = MutableLiveData<MutableList<Pair<Product, Int>>>()

    init {
        cartsList.value = mutableListOf()
    }

    fun addProduct(product: Product, count: Int) {
        val list = cartsList.value!!
        list.add(product to count)
        cartsList.postValue(list)
    }

    fun removeProduct(product: Product) {
        val list = cartsList.value!!
        list.remove(product to 1)
        cartsList.value = list
    }

    fun updateProduct(product: Product, count: Int) {
        val list = cartsList.value!!
        list.forEachIndexed { index, pair ->
            if (pair.first.id == product.id) {
                list[index] = product to pair.second + count
                cartsList.value = list
            }
        }
    }

}