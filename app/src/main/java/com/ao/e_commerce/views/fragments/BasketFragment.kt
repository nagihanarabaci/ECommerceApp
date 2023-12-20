package com.ao.e_commerce.views.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ao.e_commerce.R
import com.ao.e_commerce.databinding.FragmentBasketBinding
import com.ao.e_commerce.utils.SharedPreferencesHelper
import com.ao.e_commerce.viewmodels.BasketViewModel
import com.ao.e_commerce.viewmodels.ProductsViewModel
import com.ao.e_commerce.views.adapter.BasketAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class BasketFragment : Fragment() {
    private var _binding: FragmentBasketBinding? = null
    private var _productViewModel: ProductsViewModel? = null
    private var _viewModel:BasketViewModel? = null
    private val binding get() = _binding!!
    private val productViewModel get() = _productViewModel!!
    private val viewModel get() = _viewModel!!
    private var productTotalPrice : Int = 0
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(this)[BasketViewModel::class.java]
        _productViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        setFragmentView()
        return _binding?.root
    }

    private fun setFragmentView() {
        val sharedPref = SharedPreferencesHelper
        val sharedPreferences = sharedPref.getSharedPreferences(binding.root.context)
        val productData = sharedPref.getString(sharedPreferences, "baskets", null)

        viewModel.viewModelScope.launch {
            productViewModel.getProducts()
        }
        productData?.let {
            val splitData = productData.trim().split(",")
            if(splitData.isNotEmpty()){
                for (data in splitData) {
                    val productId = data.split("-")[0]
                    val productCount = data.split("-").last().toInt()
                    productViewModel.productsList.observe(viewLifecycleOwner) { productList ->
                        val product = productList.find { it.id.toString() == productId }

                        if (product != null && viewModel.cartsList.value?.find { it.first.id == product.id } == null) {
                            productTotalPrice += (product.price).toInt() * productCount
                            "$ $productTotalPrice".also { binding.textViewProductOfCartsTotalPrice.text = it }
                            viewModel.addProduct(product, productCount)
                            binding.buttonGoToCheckout.visibility = View.VISIBLE
                        }
                        if (splitData.last() == data) {
                            updateRecyclerView()
                        }
                    }
                }
            }
        }

        binding.buttonGoToCheckout.setOnClickListener {
            sharedPref.clearSharedPreferences(sharedPreferences)
            binding.recyclerViewCarts.layoutManager = LinearLayoutManager(binding.root.context)
            binding.recyclerViewCarts.adapter = BasketAdapter(viewModel)
            binding.textViewProductOfCartsTotalPrice.text = ""
            dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.popup_checkout)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    dialog.dismiss()
                }
            }, 3000)

            dialog.setOnDismissListener {
                val navController = findNavController()
                navController.navigate(R.id.baseFragment)
            }

        }
    }


    private fun updateRecyclerView() {
        binding.recyclerViewCarts.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerViewCarts.adapter = BasketAdapter(viewModel)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _viewModel = null
        _productViewModel = null
    }
}