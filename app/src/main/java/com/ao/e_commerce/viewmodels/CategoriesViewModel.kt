package com.ao.e_commerce.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ao.e_commerce.data.repository.ProductsRepository
import com.ao.e_commerce.models.CategoriesCardModel
import com.ao.e_commerce.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(var repository: ProductsRepository):ViewModel() {
    var categoriesList = MutableLiveData<List<CategoriesCardModel>>()
    var productsList = MutableLiveData<List<Product>>()

    fun getAllCategories(){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val list = mutableListOf<CategoriesCardModel>()
                repository.getAllCategories().forEach {
                    list.add(CategoriesCardModel(it,false))
                }
                list.add(0, CategoriesCardModel("All", true))
                categoriesList.postValue(list)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun getProductsByCategory(category:String){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val list = mutableListOf<Product>()
                repository.getProductsByCategory(category).forEach {
                    list.add(it)
                }
                productsList.postValue(list)
                Log.d("CategoriesViewModel", "getProductsByCategory: $list")
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

}