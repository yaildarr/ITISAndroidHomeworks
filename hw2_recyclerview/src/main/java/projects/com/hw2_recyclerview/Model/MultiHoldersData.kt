package projects.com.hw2_recyclerview.Model

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

sealed class MultiHoldersData(
    open val id : Int
)

class ImageTextHoldersData(
    override val id : Int,
    val imageUrl: String,
    val title: String,
    val description: String
) : MultiHoldersData(id)

class ButtonsHolderData(
    override val id : Int,
    val btn1text : String,
    val btn2text : String,
) : MultiHoldersData(id)