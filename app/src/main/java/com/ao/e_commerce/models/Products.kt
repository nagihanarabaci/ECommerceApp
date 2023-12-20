package com.ao.e_commerce.models

import java.io.Serializable

data class Products(
    val products: List<Product>,
    val total:Int,
    val skip:Int,
    val limit:Int

):Serializable

data class Product(
    val id: Long,
    val title: String,
    val description: String,
    val price: Long,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Long,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
):Serializable


