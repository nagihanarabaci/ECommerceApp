package com.ao.e_commerce.models.user

data class Bank(
    val cardExpire: String,
    val cardNumber: String,
    val cardType: String,
    val currency: String,
    val iban: String
)