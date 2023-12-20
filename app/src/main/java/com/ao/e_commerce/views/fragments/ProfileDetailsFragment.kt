package com.ao.e_commerce.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ao.e_commerce.R
import com.ao.e_commerce.databinding.FragmentProfileDetailsBinding
import com.ao.e_commerce.utils.SharedPreferencesHelper
import com.ao.e_commerce.viewmodels.ProfileViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileDetailsFragment : Fragment() {
    private var _binding: FragmentProfileDetailsBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileDetailsBinding.inflate(inflater, container, false)
        setFragmentView()
        return _binding?.root
    }

    private fun setFragmentView() {
        binding.buttonProfileDetailsBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        val userId =
            SharedPreferencesHelper
                .getSharedPreferences(requireContext())
                .getInt("user_id", 0)

        profileViewModel.getUserById(userId)
        profileViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            "${user.firstName} ${user.lastName}".also {
                binding.textViewUserFirstName.text = it
            }
            binding.textViewUserEmail.text = user.email
            binding.textViewUserPhone.text = user.phone
            binding.textViewUserName.text = user.username
            Glide.with(requireContext()).load(user.image)
                .into(binding.imageViewUserProfileDetails)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}