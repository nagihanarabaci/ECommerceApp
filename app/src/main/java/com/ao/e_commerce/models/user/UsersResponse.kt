package com.ao.e_commerce.models.user

import java.io.Serializable

data class UsersResponse(
    val users: List<Users>,
    val total:Int,
    val skip:Int,
    val limit:Int

):Serializable

data class Users(
    val address: Address,
    val age: Int,
    val bank: Bank,
    val birthDate: String,
    val bloodGroup: String,
    val company: Company,
    val domain: String,
    val ein: String,
    val email: String,
    val eyeColor: String,
    val firstName: String,
    val gender: String,
    val hair: Hair,
    val height: Int,
    val id: Int,
    val image: String,
    val ip: String,
    val lastName: String,
    val macAddress: String,
    val maidenName: String,
    val password: String,
    val phone: String,
    val ssn: String,
    val university: String,
    val userAgent: String,
    val username: String,
    val weight: Double
):Serializable
