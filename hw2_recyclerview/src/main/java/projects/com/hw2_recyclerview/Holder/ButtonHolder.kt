package projects.com.hw2_recyclerview.Holder

import android.widget.Button
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import projects.com.hw2_recyclerview.databinding.LineButtonBinding

class ButtonHolder(
    private val binding: LineButtonBinding,
) : ViewHolder(binding.root){

    fun bindButton(onViewChange: (Boolean) -> Unit){
        with(binding){
            button1.setOnClickListener {
                onViewChange(false)
            }
            button2.setOnClickListener {
                onViewChange(true)
            }
        }
    }
}