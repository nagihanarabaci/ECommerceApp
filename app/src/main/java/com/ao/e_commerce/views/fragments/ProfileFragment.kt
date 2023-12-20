package com.ao.e_commerce.views.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ao.e_commerce.R
import com.ao.e_commerce.databinding.FragmentProfileBinding
import com.ao.e_commerce.utils.SharedPreferencesHelper
import com.ao.e_commerce.utils.TokenManager
import com.ao.e_commerce.viewmodels.ProfileViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setFragmentView()
        exitAccount()
        return binding.root
    }


    private fun setFragmentView() {
        val userId =
                SharedPreferencesHelper
                .getSharedPreferences(requireContext())
                .getInt("user_id", 0)

        profileViewModel.getUserById(userId)
        profileViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            "${user.firstName} ${user.lastName}".also {
                binding.profileNameText.text = it
            }
            binding.profileEmailText.text = user.email
            Glide.with(requireContext()).load(user.image)
                .into(binding.imageViewUserProfile)

        }

        Log.d("ProfileFragment", "setFragmentView: $userId")

        with(binding){
            buttonProfileOrderPage.setOnClickListener {
                findNavController().navigate(R.id.baseToOrders)
            }

            profileEditButton.setOnClickListener {
                findNavController().navigate(R.id.baseToProfileUpdate)
            }

            buttonAddressPage.setOnClickListener {
                findNavController().navigate(R.id.baseToProfileAddress)
            }

            buttonMyDetailsPage.setOnClickListener {
                findNavController().navigate(R.id.baseToProfileDetails)
            }

            buttonPaymentMethodsPage.setOnClickListener {
                findNavController().navigate(R.id.baseToPaymentMethods)
            }
        }
    }

    private fun exitAccount(){
        val tokenManager = TokenManager(requireContext())
        binding.profileExitButton.setOnClickListener {
            tokenManager.deleteToken()
            findNavController().navigate(R.id.profileToSplash)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}