package com.example.hw5_coroutines


import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import android.Manifest
import com.example.hw5_coroutines.screen.Screen
import android.app.AlertDialog



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission()
        }

        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Screen(modifier = Modifier.padding(innerPadding))
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (!isGranted && !shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                showAlertDialog()
            }
        }

        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.needPermission))
            .setMessage(getString(R.string.requestPermission))
            .setPositiveButton(getString(R.string.Settings)) { dialog, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }
}
