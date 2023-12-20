package com.ao.e_commerce.retrofit

import com.ao.e_commerce.utils.Constants

object ApiUtils {
    fun getDummyJsonDao(): DummyJsonDao {
        return RetrofitClient.getClient(Constants.BASE_URL).create(DummyJsonDao::class.java)
    }
}