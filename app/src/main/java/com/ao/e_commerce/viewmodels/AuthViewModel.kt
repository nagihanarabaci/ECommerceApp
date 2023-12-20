package com.ao.e_commerce.viewmodels

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ao.e_commerce.data.repository.UserRepository
import com.ao.e_commerce.models.User
import com.ao.e_commerce.models.UserResponse
import com.ao.e_commerce.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val userResponseLiveData:LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData

     fun loginUser(user: User){
        Log.d("AuthViewModel", "userResponseLiveData: $userResponseLiveData")
        viewModelScope.launch {
            Log.d("AuthViewModel", "loginUser: $user")
            userRepository.loginUser(user)
        }
    }

    fun validateCredentials(userName: String, password: String,
                            isLogin: Boolean) : Pair<Boolean, String> {

        var result = Pair(true, "")
        if((!isLogin && TextUtils.isEmpty(userName)) || TextUtils.isEmpty(password)){
            result = Pair(false, "Please provide the credentials")
        }
        else if(!TextUtils.isEmpty(password) && password.length <= 5){
            result = Pair(false, "Password length should be greater than 5")
        }
        return result
    }

    fun updateUser(id:Int){
        viewModelScope.launch {
            userRepository.updateUser(id)
        }
    }
}