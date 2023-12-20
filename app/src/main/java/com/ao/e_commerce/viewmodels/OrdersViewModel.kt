package com.ao.e_commerce.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ao.e_commerce.data.repository.ProductsRepository
import com.ao.e_commerce.models.Cart
import com.ao.e_commerce.models.Carts
import com.ao.e_commerce.models.Product
import com.ao.e_commerce.models.ProductCarts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(var repository: ProductsRepository): ViewModel(){
    var listesss = MutableLiveData<List<ProductCarts>>()

    fun getUserCart(userId:Int){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val listeee = repository.getUserCart(userId)
                val list = mutableListOf<ProductCarts>()
                listeee.forEach {
                    Log.d("OrdersViewModel", "getUserCart: ${it.products}")
                    list.addAll(it.products)
                }
                val list2 = mutableListOf<ProductCarts>()
                list.forEach {
                    Log.d("OrdersViewModel", "getUserCart: ${it}")
                    list2.add(it)
                }
                listesss.postValue(list2)
            }catch (e:Exception){
                e.printStackTrace()
            }

        }

    }

}