package com.ao.e_commerce.models

data class ProductCarts(
    val discountPercentage: Double,
    val discountedPrice: Int,
    val id: Int,
    val price: Int,
    val quantity: Int,
    val thumbnail: String,
    val title: String,
    val total: Int
)