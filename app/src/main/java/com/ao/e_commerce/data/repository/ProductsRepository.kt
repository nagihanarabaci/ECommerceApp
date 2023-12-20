package com.ao.e_commerce.data.repository

import com.ao.e_commerce.data.data_source.DummyJsonDataSource
import com.ao.e_commerce.models.user.Users

class ProductsRepository(var dataSource: DummyJsonDataSource) {
    suspend fun getProducts() = dataSource.getProducts()
    suspend fun getAllCategories() = dataSource.getAllCategories()
    suspend fun getProductsByCategory(category: String) = dataSource.getProductsByCategory(category)
    suspend fun getUserById(id: Int): Users = dataSource.getUserById(id)
    suspend fun getUserCart(id: Int) = dataSource.getUserCart(id)
    suspend fun searchProducts(query: String) = dataSource.searchProducts(query)
}