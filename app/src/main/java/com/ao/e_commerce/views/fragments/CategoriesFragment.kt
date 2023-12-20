package com.ao.e_commerce.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ao.e_commerce.databinding.FragmentCategoriesBinding
import com.ao.e_commerce.viewmodels.CategoriesViewModel
import com.ao.e_commerce.views.adapter.HomeProductCardAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private var _viewModel: CategoriesViewModel? = null

    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentCategoriesBinding.inflate(inflater,container,false)
        _viewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
        setFragmentView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonBackCategories.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
    private fun setFragmentView(){
        val bundle : CategoriesFragmentArgs by navArgs()
        val category = bundle.category
        viewModel.getProductsByCategory(category.name)
        viewModel.productsList.observe(viewLifecycleOwner){productsList->
            binding.recyclerViewCategoryByProduct.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            binding.recyclerViewCategoryByProduct.adapter = HomeProductCardAdapter(productsList,true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _viewModel = null
    }









}