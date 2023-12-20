package com.ao.e_commerce.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ao.e_commerce.R
import com.ao.e_commerce.databinding.FragmentOrdersBinding
import com.ao.e_commerce.utils.SharedPreferencesHelper
import com.ao.e_commerce.viewmodels.OrdersViewModel
import com.ao.e_commerce.views.adapter.FavoriteAdapter
import com.ao.e_commerce.views.adapter.OrdersAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrdersFragment : Fragment() {
    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrdersViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        setFragmentView()
        return _binding?.root
    }

    private fun setFragmentView() {
        binding.buttonProfileOrdersBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        val userId =
            SharedPreferencesHelper
                .getSharedPreferences(requireContext())
                .getInt("user_id", 0)
        viewModel.getUserCart(userId)
        Log.d("OrdersFragment", "setFragmentView: $userId")
        Log.d("OrdersFragment", "setFragmentView: ${viewModel.listesss.value}")
        viewModel.listesss.observe(viewLifecycleOwner) { orders ->
            Log.d("OrdersFragment", "setFragmentView: $orders")
            binding.recyclerViewOrders.layoutManager = LinearLayoutManager(binding.root.context)
            binding.recyclerViewOrders.adapter = OrdersAdapter(orders)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

