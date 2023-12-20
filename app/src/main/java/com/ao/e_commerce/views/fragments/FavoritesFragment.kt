package com.ao.e_commerce.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ao.e_commerce.R
import com.ao.e_commerce.databinding.FragmentFavoritesBinding
import com.ao.e_commerce.models.Product
import com.ao.e_commerce.utils.SharedPreferencesHelper
import com.ao.e_commerce.viewmodels.FavoriteViewModel
import com.ao.e_commerce.viewmodels.ProductsViewModel
import com.ao.e_commerce.views.adapter.FavoriteAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private var _binding : FragmentFavoritesBinding? = null
    private var _viewModel : FavoriteViewModel? = null
    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentFavoritesBinding.inflate(inflater,container,false)
        _viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        setFragmentView()
        return _binding?.root
    }

    private fun setFragmentView(){
        viewModel.getFavorite(requireContext())
        viewModel.favoriteList.observe(viewLifecycleOwner){
            it?.let {
                val favoriteAdapter = FavoriteAdapter(it,viewModel)
                binding.recyclerViewFavorite.layoutManager = LinearLayoutManager(binding.root.context)
                binding.recyclerViewFavorite.adapter = favoriteAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _viewModel = null
    }
}