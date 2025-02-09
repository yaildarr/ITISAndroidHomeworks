package com.example.hw6_room.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.hw6_room.R
import com.example.hw6_room.base.BaseActivity
import com.example.hw6_room.databinding.FragmentLoginBinding
import com.example.hw6_room.databinding.FragmentRegistrationBinding
import com.example.hw6_room.di.ServiceLocator
import com.example.hw6_room.util.AuthPreferences
import com.example.hw6_room.util.NavigationAction
import com.google.android.material.snackbar.Snackbar
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)

    private val userRepository = ServiceLocator.getUserRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ButtonLogin.setOnClickListener {
            val email = binding.emailInput.editText?.text.toString().trim()
            val password = binding.passwordInput.editText?.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    val user = userRepository.getUserByEmailAndPassword(email, password)

                    if (user != null) {
                        AuthPreferences.saveUserId(requireContext(), user.id)
                        navigateToHome(user.id)
                    } else {
                        withContext(Dispatchers.Main) {
                            showSnackbar(getString(R.string.Incorrect_email_or_password))
                        }
                    }
                }
            } else {
                showSnackbar(getString(R.string.enter_all_lines))
            }
        }


        binding.btnRegister.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateToRegister() {
        (requireActivity() as? BaseActivity)?.navigate(
            destination = RegistrationFragment(),
            destinationTag = REGISTER_FRAGMENT_TAG,
            action = NavigationAction.REPLACE,
            isAddToBackStack = true,
            isUseAnimation = true
        )
    }

    private fun navigateToHome(userId : Long){
        (requireActivity() as? BaseActivity)?.navigate(
            destination = HomeFragment(userId = userId),
            destinationTag = HOME_FRAGMENT_TAG,
            action = NavigationAction.REPLACE,
            isAddToBackStack = true,
            isUseAnimation = true
        )
    }

    companion object{
        const val HOME_FRAGMENT_TAG = "HomeFragment"
        const val REGISTER_FRAGMENT_TAG = "RegisterFragment"
    }
}