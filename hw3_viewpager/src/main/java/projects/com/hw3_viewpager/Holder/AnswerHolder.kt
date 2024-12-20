package projects.com.hw3_viewpager.Holder

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import projects.com.hw3_viewpager.Model.Option
import projects.com.hw3_viewpager.Repository.QuestionRepository
import projects.com.hw3_viewpager.databinding.RecycleItemBinding

class AnswerHolder(
    val binding: RecycleItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.apply {
            optionRadioButton.buttonTintList = ColorStateList(
                arrayOf(
                    intArrayOf(-R.attr.state_checked),
                    intArrayOf(R.attr.state_checked)
                ),
                intArrayOf(
                    Color.BLACK,
                    Color.WHITE
                )
            )

        }
    }

        fun bindItem(item: Option, isSelected: Boolean, action: () -> Unit) {
            binding.apply {
                optionText.text = item.text
                optionRadioButton.isChecked = isSelected

                itemCard.setCardBackgroundColor(
                    ContextCompat.getColor(
                        root.context,
                        if (isSelected) R.color.system_accent2_500 else R.color.white
                    )
                )

                optionRadioButton.setOnClickListener {
                    action()
                }
                itemCard.setOnClickListener {
                    action()
                }
            }
        }
}