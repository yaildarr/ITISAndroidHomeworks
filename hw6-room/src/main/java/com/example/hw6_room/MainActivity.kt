package com.example.hw6_room

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hw6_room.base.BaseActivity
import com.example.hw6_room.fragment.HomeFragment
import com.example.hw6_room.fragment.LoginFragment
import com.example.hw6_room.fragment.RegistrationFragment
import com.example.hw6_room.util.AuthPreferences
import com.example.hw6_room.util.NavigationAction

class MainActivity : BaseActivity() {

    override val mainContainerId: Int = R.id.mainContainer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


            val userId = AuthPreferences.getUserId(this)
            if (userId != null){
                navigate(
                    destination = HomeFragment(userId),
                    destinationTag = HOME_FRAGMENT_TAG,
                    action = NavigationAction.ADD,
                    isAddToBackStack = false
                )
            } else {
                navigate(
                    destination = LoginFragment(),
                    destinationTag = LOGIN_FRAGMENT_TAG,
                    action = NavigationAction.ADD,
                    isAddToBackStack = false
                )
            }
    }



    companion object{
        const val HOME_FRAGMENT_TAG = "HomeFragment"
        const val LOGIN_FRAGMENT_TAG = "LoginFragment"
    }
}
