package com.ao.e_commerce.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ao.e_commerce.data.repository.ProductsRepository
import com.ao.e_commerce.models.Product
import com.ao.e_commerce.models.user.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(var repository: ProductsRepository): ViewModel() {
    val currentUser = MutableLiveData<Users>()

    fun getUserById(userId:Int){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val users = repository.getUserById(userId)
                currentUser.postValue(users)
                Log.d("ProfileViewModel", "getUserById: $users")
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}