package com.ao.e_commerce.data.data_source

import android.util.Log
import com.ao.e_commerce.models.Cart
import com.ao.e_commerce.models.Carts

import com.ao.e_commerce.models.Product

import com.ao.e_commerce.models.user.Users

import com.ao.e_commerce.retrofit.DummyJsonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DummyJsonDataSource(var dao: DummyJsonDao) {

    suspend fun getProducts(): List<Product> =
        withContext(Dispatchers.IO) {
            return@withContext dao.getProducts().products

        }

    suspend fun getAllCategories(): List<String> =
        withContext(Dispatchers.IO) {
            return@withContext dao.getAllCategories()

        }

    suspend fun getProductsByCategory(category: String): List<Product> =
        withContext(Dispatchers.IO) {
            return@withContext dao.getProductsByCategory(category).products

        }

    suspend fun getUserById(id: Int): Users =
        withContext(Dispatchers.IO) {
            return@withContext dao.getUserById(id)
        }

    suspend fun getUserCart(id: Int): List<Cart> =
        withContext(Dispatchers.IO) {
            Log.d("DummyJsonDataSource", "getUserCart: ${dao.getUserCart(id)}  $id")
            return@withContext dao.getUserCart(id).carts
        }

    suspend fun searchProducts(query: String): List<Product> =
        withContext(Dispatchers.IO) {
            return@withContext dao.searchProducts(query).products
        }



}