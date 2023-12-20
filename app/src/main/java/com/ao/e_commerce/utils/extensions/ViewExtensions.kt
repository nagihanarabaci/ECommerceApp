package com.ao.e_commerce.utils.extensions

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ao.e_commerce.R
import com.ao.e_commerce.views.fragments.LoadingDialogFragment
import com.google.android.material.snackbar.Snackbar

fun View.hideKeyboard() {
    setOnClickListener {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
        clearFocus()
    }
}

fun View.showToast(message: String) {
    Toast.makeText(
        this.context, message, Toast.LENGTH_SHORT
    ).show()
}

fun View.showSnackbar(message:String, action:() -> Unit){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .setBackgroundTint(resources.getColor(R.color.primary,null))
        .setTextColor(resources.getColor(R.color.white,null))
        .setActionTextColor(resources.getColor(R.color.lightGrey,null))
        .setAction("Ok!") {
            action()
        }.show()
}

fun View.showLoadingDialog(manager:FragmentManager){
    LoadingDialogFragment(this).show(manager, "loading")
}

fun View.hideLoadingDialog(manager:FragmentManager){
    val loadingDialogFragment = manager.findFragmentByTag("loading")
    if (loadingDialogFragment != null) {
        (loadingDialogFragment as LoadingDialogFragment).dismiss()
    }
}