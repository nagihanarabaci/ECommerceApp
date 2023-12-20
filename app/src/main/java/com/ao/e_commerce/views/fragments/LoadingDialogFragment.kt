package com.ao.e_commerce.views.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ao.e_commerce.R

class LoadingDialogFragment(private val group: View) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = Dialog(requireContext())
        builder.layoutInflater.inflate(R.layout.loading_layout, group as ViewGroup)
        return builder
    }
}