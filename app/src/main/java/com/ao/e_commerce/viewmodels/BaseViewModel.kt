package com.ao.e_commerce.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ao.e_commerce.utils.enums.BottomNavigationBar
import dagger.hilt.android.lifecycle.HiltViewModel

class BaseViewModel:ViewModel() {
    var activeTab:BottomNavigationBar = BottomNavigationBar.HOME

    fun changeActiveTab(tab:BottomNavigationBar){
        activeTab = tab
        Log.i("BaseViewModel", "changeActiveTab: $activeTab")
    }
}