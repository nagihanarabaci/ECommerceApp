package com.ao.e_commerce.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.ao.e_commerce.utils.Constants.pref_name

object SharedPreferencesHelper {
    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(pref_name, Context.MODE_PRIVATE)
    }

    fun put(sharedPreferences: SharedPreferences, key: String, value: Any) {
        val editor = sharedPreferences.edit()
        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            is Boolean -> editor.putBoolean(key, value)
        }
        editor.commit()
    }

    fun getString(
        sharedPreferences: SharedPreferences, key: String, defaultValue: String?
    ): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun saveUser(sharedPreferences: SharedPreferences, userId: Int) {
        sharedPreferences.edit().putInt("user_id", userId).apply()
    }

    fun productAdd(sharedPreferences: SharedPreferences, productId: String) {
        val existingData = sharedPreferences.getString("product_data", null)
        var productCount = 0
        if (existingData != null && existingData.trim().isNotEmpty()) {
            val productEntries = existingData.split(",")
            val updatedEntries = mutableListOf<String>()
            for (entry in productEntries) {
                val parts = entry.split("-")
                if (parts.size == 2 && parts[0] == productId) {
                    productCount = parts[1].toInt() + 1
                    updatedEntries.add("$productId-$productCount")
                } else {
                    updatedEntries.add(entry)
                }
            }
            if (productCount == 0) {
                updatedEntries.add("$productId-${productCount + 1}")
            }
            val updatedData = updatedEntries.joinToString(",")
            sharedPreferences.edit().putString("product_data", updatedData).apply()

        } else {
            productCount = 1
            val newData = "$productId-$productCount"
            sharedPreferences.edit().putString("product_data", newData).apply()
        }
    }

    fun addToBasket(
        sharedPreferences: SharedPreferences, productId: String, productCount: String = "1"
    ) {
        val basketData = sharedPreferences.getString("baskets", null)
        fun isCheckout(): Boolean {
            return basketData != null && basketData.trim().isNotEmpty()
        }

        fun addData(value: String) {
            sharedPreferences.edit().putString("baskets", value).apply()
        }
        if (isCheckout()) {
            if (basketData!!.contains(productId)) {
                val list = basketData.split(",")
                list.forEach {
                    val parts = it.split("-")
                    if (parts[0] == productId) {
                        val newCount = parts[1].toInt() + productCount.toInt()
                        addData(basketData.replace(it, "$productId-$newCount"))
                    }
                }
            } else {
                addData("$basketData,${"$productId-$productCount"}")
            }
        } else {
            addData("$productId-$productCount")
        }
    }

    fun deleteFromBasket(sharedPreferences: SharedPreferences, productId: String) {
        val basketData = sharedPreferences.getString("baskets", null)
        if (basketData != null && basketData.trim().isNotEmpty()) {
            val list = basketData.split(",")
            val updatedList = mutableListOf<String>()
            list.forEach {
                val parts = it.split("-")
                if (parts[0] != productId) {
                    updatedList.add(it)
                }
            }
            val updatedData = updatedList.joinToString(",")
            sharedPreferences.edit().putString("baskets", updatedData).apply()
        }
    }

    fun clearSharedPreferences(sharedPreferences: SharedPreferences) {
        sharedPreferences.edit().clear().apply()
    }

    fun putFavorites(sharedPreferences: SharedPreferences, value: String) {
        put(sharedPreferences, "favorites", value)
    }

    fun getFavorites(sharedPreferences: SharedPreferences): String? {
        return getString(sharedPreferences, "favorites", "")
    }


}
