package com.ao.e_commerce.retrofit

import com.ao.e_commerce.models.Carts

import com.ao.e_commerce.models.Products
import com.ao.e_commerce.models.User
import com.ao.e_commerce.models.UserResponse
import com.ao.e_commerce.models.user.Users
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DummyJsonDao {

    @POST("auth/login")
    suspend fun login(@Body user:User): Response<UserResponse>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category:String): Products

    @GET("products")
    suspend fun getProducts(): Products

    @GET("products/categories")
    suspend fun getAllCategories(): List<String>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id:Int): Users

    @GET("carts/user/{id}")
    suspend fun getUserCart(@Path("id") id:Int): Carts

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id:Int): Response<UserResponse>

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query:String): Products

}