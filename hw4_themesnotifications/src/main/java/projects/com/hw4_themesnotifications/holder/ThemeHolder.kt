package projects.com.hw4_themesnotifications.holder

import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import projects.com.hw4_themesnotifications.databinding.ItemThemeBinding
import projects.com.hw4_themesnotifications.model.Theme


class ThemeHolder(
    private val binding: ItemThemeBinding,
    private val action : (Int) -> Unit
) : ViewHolder(binding.root){

    init{
        binding.themeButton.setOnClickListener {
            Log.d("MyLog","setOnClickListener position ${adapterPosition}")
            action.invoke(adapterPosition)
        }
    }

    fun bindItem(item: Theme){
        Log.d("MyLog","Binding item: ${item.color}")
        with(binding){
            themeButton.background = ColorDrawable(item.color)
        }
    }

}