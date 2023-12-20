package com.ao.e_commerce.models

data class Cart(
    val discountedTotal: Int,
    val id: Int,
    val products: List<ProductCarts>,
    val total: Int,
    val totalProducts: Int,
    val totalQuantity: Int,
    val userId: Int
)