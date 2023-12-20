package com.ao.e_commerce.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ao.e_commerce.R
import com.ao.e_commerce.databinding.FragmentLoginBinding
import com.ao.e_commerce.models.User
import com.ao.e_commerce.utils.NetworkResult
import com.ao.e_commerce.utils.SharedPreferencesHelper
import com.ao.e_commerce.utils.TokenManager
import com.ao.e_commerce.utils.extensions.hideKeyboard
import com.ao.e_commerce.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observer
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var tokenManager: TokenManager
    private val authViewModel by viewModels<AuthViewModel>()
    private val sharedPref = SharedPreferencesHelper



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        closeKeyboard()
        loginFunction()
        return _binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()

    }

    private fun loginFunction(){
        binding.btnSignIn.setOnClickListener {
            val validationResult = validateUserInput()
            if(validationResult.first){
                val userRequest = getUserRequest()
                authViewModel.loginUser(userRequest)
            }
            else{
                Log.d("LoginFragment", "onViewCreated: ${validationResult.second}")
                Toast.makeText(requireContext(), validationResult.second, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUserRequest(): User {
        return binding.run {
            User(
                textInputName.text.toString(),
                textInputPassword.text.toString(),
            )
        }
    }
    private fun validateUserInput(): Pair<Boolean, String> {
        val userName = binding.textInputName.text.toString()
        val password = binding.textInputPassword.text.toString()
        Log.d("LoginFragment", "validateUserInput: $userName $password")
        return authViewModel.validateCredentials(userName,password, true)
    }

    private fun bindObservers() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner){
            when (it) {
                is NetworkResult.Success -> {
                    tokenManager.saveToken(it.data!!.token)
                    val sharedPreferences = sharedPref.getSharedPreferences(requireContext())
                    sharedPref.saveUser(sharedPreferences, it.data.id)
                    it.data.id
                    findNavController().navigate(R.id.baseFragment)
                }
                is NetworkResult.Error -> {
                    Log.d("LoginFragment", "bindObservers: ${it.message}")
                }
                else -> {
                    Log.d("LoginFragment", "bindObservers: Something went wrong")
                }
            }
        }
    }


    private fun closeKeyboard(){
        binding.root.hideKeyboard()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}