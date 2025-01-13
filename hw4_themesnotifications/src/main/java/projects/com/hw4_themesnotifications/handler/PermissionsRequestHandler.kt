package projects.com.hw4_themesnotifications.handler

import androidx.activity.result.contract.ActivityResultContracts
import projects.com.hw4_themesnotifications.base.BaseActivity

class PermissionsRequestHandler(
    activity: BaseActivity,
    private val callback: (() -> Unit)? = null,
    private val rationaleCallback: (() -> Unit)? = null,
    private val denidedCallback: (() -> Unit)? = null
) {

    private var currentPermission = ""

    private val singlePermissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
        if (isGranted){
            callback?.invoke()
        } else {
            if(currentPermission.isNotEmpty() && activity.shouldShowRequestPermissionRationale(currentPermission)){
                rationaleCallback?.invoke()
            } else {
                 denidedCallback?.invoke()
            }
        }
    }


    fun requestPermission(permission: String){
        this.currentPermission = permission
        singlePermissionLauncher.launch(permission)
    }

}