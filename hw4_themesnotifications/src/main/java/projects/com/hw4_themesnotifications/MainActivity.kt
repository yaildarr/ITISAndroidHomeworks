package projects.com.hw4_themesnotifications

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.PermissionRequest
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import projects.com.hw4_themesnotifications.base.BaseActivity
import projects.com.hw4_themesnotifications.fragment.MainFragment
import projects.com.hw4_themesnotifications.fragment.MainFragment.Companion.ALREADY_UPLOAD
import projects.com.hw4_themesnotifications.handler.NotificationHandler
import projects.com.hw4_themesnotifications.handler.PermissionsRequestHandler
import projects.com.hw4_themesnotifications.util.NavigationAction

class MainActivity : BaseActivity() {

    override val mainContainerId: Int = R.id.main_container

    private var permissionRequestHandler: PermissionsRequestHandler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        val theme = intent.getIntExtra(EXTRA_THEME,0)
        when (theme){
            0 -> setTheme(R.style.Base_Theme_ITISAndroidHomeworks)
            1 -> setTheme(R.style.Base_Theme_ITISAndroidHomeworks_red)
            2 -> setTheme(R.style.Base_Theme_ITISAndroidHomeworks_green)
            3 -> setTheme(R.style.Base_Theme_ITISAndroidHomeworks_yellow)
            else -> setTheme(R.style.Base_Theme_ITISAndroidHomeworks)
        }
        if (intent.getBooleanExtra(EXTRA_TOAST,false)){
            Log.d("MyLog","notificationtrue")
            Toast.makeText(this, FROM_NOTIFICATION, Toast.LENGTH_SHORT).show()
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null){
            navigate(
                destination = MainFragment(),
                destinationTag = "mainFragment",
                action = NavigationAction.ADD
            )
        }
        permissionRequestHandler = PermissionsRequestHandler(
            activity = this,
            callback = null
        )
        this.requestPermission(android.Manifest.permission.POST_NOTIFICATIONS)
        this.requestPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        NotificationHandler().createNotificationChannels(this)
    }

    override fun onNewIntent(intent: Intent) {
        Log.d("MyLog","onNewIntet")
        super.onNewIntent(intent)
        if (intent.getBooleanExtra(EXTRA_TOAST,false)){
            Toast.makeText(this, FROM_NOTIFICATION, Toast.LENGTH_SHORT).show()
        }
    }

    fun requestPermission(permission: String){
        permissionRequestHandler?.requestPermission(permission)
    }



    companion object {
        const val EXTRA_THEME = "theme"
        const val EXTRA_TOAST = "show_toast"
        const val FROM_NOTIFICATION = "Запущено с уведомления"
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}