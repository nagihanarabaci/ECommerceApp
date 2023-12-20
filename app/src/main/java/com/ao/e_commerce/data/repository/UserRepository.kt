package com.ao.e_commerce.data.repository

import android.net.wifi.WifiManager.AddNetworkResult
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ao.e_commerce.models.User
import com.ao.e_commerce.models.UserResponse
import com.ao.e_commerce.retrofit.DummyJsonDao
import com.ao.e_commerce.utils.NetworkResult
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val dummyJsonDao: DummyJsonDao) {
    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData


    suspend fun loginUser(user: User){
        _userResponseLiveData.postValue(NetworkResult.Loading())
        Log.d("UserRepository", "loginUser: $user")
        try {
            val response = dummyJsonDao.login(user)
            Log.d("UserRepository", "loginUser: $response")
            handleResponse(response)
        } catch (e: Exception) {
            _userResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        }
        else if(response.errorBody()!=null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        }
        else{
            _userResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    suspend fun updateUser(id:Int){
        _userResponseLiveData.postValue(NetworkResult.Loading())
        try {
            val response = dummyJsonDao.updateUser(id)
            Log.d("UserRepository", "updateUser: $response")
//            if (response.isSuccessful && response.body() != null) {
//                _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
//
//            }
//            else if(response.errorBody()!=null){
//                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
//                _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
//            }
//            else{
//                _userResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
//            }
            handleResponse(response)
        } catch (e: Exception) {
            _userResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }
}