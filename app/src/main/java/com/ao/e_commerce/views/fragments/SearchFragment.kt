package com.ao.e_commerce.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ao.e_commerce.databinding.FragmentSearchBinding
import com.ao.e_commerce.models.Product
import com.ao.e_commerce.utils.extensions.hideKeyboard
import com.ao.e_commerce.viewmodels.ProductsViewModel
import com.ao.e_commerce.views.adapter.HomeProductCardAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private var _viewModel: ProductsViewModel? = null
    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!
    private val productList = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        setRecyclerView()
        searchOptions()
        return _binding?.root
    }

    private fun setRecyclerView() {
        productList.clear()
        viewModel.getProducts()
        with(binding) {
            searchRecyclerView.setHasFixedSize(false)
            searchRecyclerView.isNestedScrollingEnabled = false
            searchRecyclerView.hideKeyboard()
            viewModel.productsList.observe(viewLifecycleOwner) {
                productList.clear()
                productList.addAll(it)
                if (searchView.text.trim().isNotEmpty()) {
                    search()
                }
            }
        }
    }

    private fun searchOptions() {
        with(binding) {
            searchView.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    && searchView.text.trim().isNotEmpty()
                ) {
                    search()
                }
                searchView.hideKeyboard()
                false
            }
        }
    }

    private fun search() {
        with(binding) {
            searchRecyclerView.adapter = null
            val filteredList = mutableListOf<Product>()
            Log.e("SearchFragment", "search: ${productList.toString()}")

            val filterProductsList = productList.filter {
                it.title.trim().lowercase()
                    .contains(searchView.text.toString().trim().lowercase(), true)
            }
            Log.e("SearchFragment", "search: ${filterProductsList.size}")
            filteredList.addAll(filterProductsList)
            searchRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            searchRecyclerView.adapter = HomeProductCardAdapter(filteredList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _viewModel = null
    }

}