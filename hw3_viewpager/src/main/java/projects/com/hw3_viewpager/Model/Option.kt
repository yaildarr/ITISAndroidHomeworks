package projects.com.hw3_viewpager.Model

import android.os.Parcelable

data class Option(
    val id: Int,
    val text: String,
    val isChecked: Boolean = false
)