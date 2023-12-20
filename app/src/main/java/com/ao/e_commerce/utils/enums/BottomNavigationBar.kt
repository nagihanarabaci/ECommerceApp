package com.ao.e_commerce.utils.enums

import androidx.fragment.app.Fragment
import com.ao.e_commerce.R
import com.ao.e_commerce.views.fragments.BasketFragment
import com.ao.e_commerce.views.fragments.SearchFragment
import com.ao.e_commerce.views.fragments.FavoritesFragment
import com.ao.e_commerce.views.fragments.HomeFragment
import com.ao.e_commerce.views.fragments.ProfileFragment

enum class BottomNavigationBar(var imageResource: Int, var fragment: Fragment? = null) {
    HOME(R.drawable.im_shop, HomeFragment()),
    SEARCH(R.drawable.im_search, SearchFragment()),
    CART(R.drawable.im_cart, BasketFragment()),
    FAVORITE(R.drawable.im_favorite, FavoritesFragment()),
    PROFILE(R.drawable.im_profile, ProfileFragment())
}