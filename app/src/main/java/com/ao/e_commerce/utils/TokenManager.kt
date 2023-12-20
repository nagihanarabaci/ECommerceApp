package com.ao.e_commerce.utils

import android.content.Context
import android.util.Log
import com.ao.e_commerce.utils.Constants.prefs_token_file
import com.ao.e_commerce.utils.Constants.user_token
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences = context.getSharedPreferences(prefs_token_file, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(user_token, token).apply()
        Log.d("TokenManager", "saveToken: $token")
    }

    fun getToken(): String? {
        Log.d("TokenManager", "saveTokennnn: $sharedPreferences.getString(USER_TOKEN, null)")
        return sharedPreferences.getString(user_token, null)
    }

    fun deleteToken(){
        sharedPreferences.edit().remove(user_token).apply()
    }
}