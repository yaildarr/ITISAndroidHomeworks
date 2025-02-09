package com.example.hw6_room.fragment


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.hw6_room.R
import com.example.hw6_room.base.BaseActivity
import com.example.hw6_room.data.entities.UserEntity
import com.example.hw6_room.databinding.FragmentRegistrationBinding
import com.example.hw6_room.di.ServiceLocator
import com.example.hw6_room.util.AuthPreferences
import com.example.hw6_room.util.NavigationAction
import com.google.android.material.snackbar.Snackbar
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch


class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val binding: FragmentRegistrationBinding by viewBinding(FragmentRegistrationBinding::bind)

    private val userRepository = ServiceLocator.getUserRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonRegistration.setOnClickListener {
            registerUser()
        }

        binding.textViewLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun registerUser(){
        val name = binding.nameInput.editText?.text.toString().trim()
        val surname = binding.surnameInput.editText?.text.toString().trim()
        val email = binding.emailInput.editText?.text.toString().trim()
        val password = binding.passwordInput.editText?.text.toString().trim()
        val confirmPassword = binding.passwordConfirmInput.editText?.text.toString().trim()

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showSnackbar(getString(R.string.All_fields_are_required))
            return
        }

        if (!isValidEmail(email)) {
            showSnackbar(getString(R.string.incorrect_email))
            return
        }

        if (password != confirmPassword) {
            showSnackbar(getString(R.string.passwords_dont_match))
            return
        }

        val user = UserEntity(
            name = name,
            surname = surname,
            email = email,
            password = password
        )

        lifecycleScope.launch {
            try {
                var userId = userRepository.saveUser(user)
                showSnackbar(getString(R.string.register_succes))
                AuthPreferences.saveUserId(requireContext(), userId)
                navigateToHome(userId)
            } catch (e: Exception) {
                showSnackbar(getString(R.string.register_error, e.message))
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateToLogin() {
        (requireActivity() as? BaseActivity)?.navigate(
            destination = LoginFragment(),
            destinationTag = LOGIN_FRAGMENT_TAG,
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
        const val LOGIN_FRAGMENT_TAG = "LoginFragment"
    }
}