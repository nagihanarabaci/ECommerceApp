package com.ao.e_commerce.models

data class Carts(
    val carts: List<Cart>,
    val limit: Int,
    val skip: Int,
    val total: Int
)