package com.ao.e_commerce.models.user

import com.ao.e_commerce.models.user.Address

data class Company(
    val address: Address,
    val department: String,
    val name: String,
    val title: String
)