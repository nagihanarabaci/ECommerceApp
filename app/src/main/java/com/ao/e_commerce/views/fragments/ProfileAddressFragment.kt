package com.ao.e_commerce.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ao.e_commerce.R
import com.ao.e_commerce.databinding.FragmentProfileAddressBinding
import com.ao.e_commerce.utils.SharedPreferencesHelper
import com.ao.e_commerce.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileAddressFragment : Fragment() {
    private var _binding: FragmentProfileAddressBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileAddressBinding.inflate(inflater, container, false)
        setFragmentView()
        return _binding?.root
    }

    private fun setFragmentView() {
        binding.buttonProfileAddressBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
            val userId =
                    SharedPreferencesHelper
                    .getSharedPreferences(requireContext())
                    .getInt("user_id", 0)

            profileViewModel.getUserById(userId)
        profileViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            "${user.address.address } / ${user.address.city }".also {
                binding.textViewAddresSt.text = it
            }
            "Postal Code: ${user.address.postalCode}".also {
                binding.textViewPostalCode.text = it
            }

        }

    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}