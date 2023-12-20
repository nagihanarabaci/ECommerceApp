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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.ao.e_commerce.R
import com.ao.e_commerce.databinding.FragmentProfileUpdateBinding
import com.ao.e_commerce.utils.NetworkResult
import com.ao.e_commerce.utils.SharedPreferencesHelper
import com.ao.e_commerce.utils.extensions.hideKeyboard
import com.ao.e_commerce.utils.extensions.hideLoadingDialog
import com.ao.e_commerce.utils.extensions.showLoadingDialog
import com.ao.e_commerce.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class ProfileUpdateFragment : Fragment() {
    private var _binding: FragmentProfileUpdateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var dialog: Dialog




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileUpdateBinding.inflate(inflater, container, false)
        setFragmentView()
        binding.profileUpdateBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.root.hideKeyboard()
        return _binding?.root
    }

    private fun setFragmentView() {

        val userId =
            SharedPreferencesHelper
                .getSharedPreferences(requireContext())
                .getInt("user_id", 0)

        binding.buttonProfleUpdate.setOnClickListener {
            viewModel.updateUser(userId)
            Log.d("ProfileUpdateFragment", "setFragmentView: $userId")
            viewModel.userResponseLiveData.observe(viewLifecycleOwner) {Log.d("ProfileUpdateFragment", "setFragmentView: $it")
                when (it) {
                    is NetworkResult.Loading -> {
                        Log.d("ProfileUpdateFragment", "setFragmentView: Loading")

                        binding.root.showLoadingDialog(childFragmentManager)
                    }
                    is NetworkResult.Error -> {
                        Log.d("ProfileUpdateFragment", "setFragmentView: Error")
                        binding.root.hideLoadingDialog(childFragmentManager)

                    }
                    is NetworkResult.Success -> {
                        binding.root.hideLoadingDialog(childFragmentManager)
                        dialog = Dialog(requireContext())
                        dialog.setContentView(R.layout.popup_profile_update)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.show()
                        val timer = Timer()
                        timer.schedule(object : TimerTask() {
                            override fun run() {
                                dialog.dismiss()
                            }

                        }, 3000)

                        dialog.setOnDismissListener {
                            requireActivity().onBackPressedDispatcher.onBackPressed()
                        }
                    }
                }
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}