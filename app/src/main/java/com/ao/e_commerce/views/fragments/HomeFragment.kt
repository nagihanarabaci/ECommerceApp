package com.ao.e_commerce.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ao.e_commerce.databinding.FragmentHomeBinding
import com.ao.e_commerce.utils.enums.BottomNavigationBar
import com.ao.e_commerce.viewmodels.CategoriesViewModel
import com.ao.e_commerce.viewmodels.ProductsViewModel
import com.ao.e_commerce.views.adapter.HomeCategoryCardAdapter
import com.ao.e_commerce.views.adapter.HomeProductCardAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private var _viewModel: ProductsViewModel? = null
    private var _categoryViewModel: CategoriesViewModel? = null

    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!
    private val categoryViewModel get() = _categoryViewModel!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        _categoryViewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
        searchViewOptions()
        categoryRecyclerViewOptions()

        return binding.root
    }

    private fun recyclerViewOptions() {
        viewModel.getProducts()
        with(binding) {
            homeProductsRecyclerView.setHasFixedSize(false)
            homeProductsRecyclerView.isNestedScrollingEnabled = false
            viewModel.productsList.observe(viewLifecycleOwner) { productList ->
                homeProductsRecyclerView.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                homeProductsRecyclerView.adapter = HomeProductCardAdapter(productList)
            }
        }
    }

    private fun categoryRecyclerViewOptions() {
        categoryViewModel.getAllCategories()
        with(binding) {
            homeCategoryRecyclerView.setHasFixedSize(false)
            homeCategoryRecyclerView.isNestedScrollingEnabled = false
            categoryViewModel.categoriesList.observe(viewLifecycleOwner) { categoryList ->
                homeCategoryRecyclerView.adapter =
                    HomeCategoryCardAdapter(binding, categoryList, categoryViewModel)
                recyclerViewOptions()
            }
        }
    }

    private fun searchViewOptions() {
        binding.homeSearchViewCard.setOnClickListener {
            val baseFragment: BaseFragment = parentFragment as BaseFragment
            baseFragment.changeActiveTab(BottomNavigationBar.SEARCH)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


