package com.ao.e_commerce.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.setPadding
import androidx.lifecycle.ViewModelProvider
import com.ao.e_commerce.R
import com.ao.e_commerce.databinding.FragmentBaseBinding
import com.ao.e_commerce.utils.enums.BottomNavigationBar
import com.ao.e_commerce.viewmodels.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseFragment : Fragment() {
    private var _binding: FragmentBaseBinding? = null
    private var _viewModel: BaseViewModel? = null

    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBaseBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(this)[BaseViewModel::class.java]
        bottomNavBarOptions()
        return binding.root
    }

    private fun bottomNavBarOptions() {
        binding.baseBottomNavBarLinearLayout.removeAllViews()
        BottomNavigationBar.entries.forEach {
            val imageView = ImageView(binding.root.context)
            imageView.setImageResource(it.imageResource)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.weight = 1f
            imageView.layoutParams = params
            imageView.setOnClickListener { iv ->
                val index = binding.baseBottomNavBarLinearLayout.indexOfChild(iv)
                viewModel.changeActiveTab(BottomNavigationBar.entries[index])
                viewModel.activeTab.fragment?.let { it1 -> changeFragment(it1) }
                bottomNavBarOptions()
            }
            binding.baseBottomNavBarLinearLayout.addView(imageView)
            if (viewModel.activeTab == it) {
                imageView.setColorFilter(resources.getColor(R.color.primary, null))
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.baseFragmentContainer, fragment)
            .commit()
    }

    fun changeActiveTab(tab: BottomNavigationBar) {
        viewModel.changeActiveTab(tab)
        bottomNavBarOptions()
        changeFragment(tab.fragment!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _viewModel = null
    }
}