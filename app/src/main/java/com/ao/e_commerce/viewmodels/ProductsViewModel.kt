package com.ao.e_commerce.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ao.e_commerce.data.repository.ProductsRepository
import com.ao.e_commerce.models.Product
import com.ao.e_commerce.utils.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(var repository: ProductsRepository) : ViewModel(){
    var productsList = MutableLiveData<List<Product>>()


    fun getProducts(){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                productsList.postValue(repository.getProducts())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}