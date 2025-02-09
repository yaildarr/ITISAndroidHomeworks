package com.example.hw6_room.holder

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.hw6_room.data.entities.TransactionEntity
import com.example.hw6_room.databinding.ItemListBinding

class TransactionHolder(
    val binding: ItemListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: TransactionEntity){
        binding.apply {
            titleTextView.text = item.title
            amountTextView.text = if (item.category == "Доход") {
                "+${item.amount} ₽"
            } else {
                "-${item.amount} ₽"
            }
            amountTextView.setTextColor(
                if (item.category == "Доход") Color.parseColor("#34C759")
                else Color.parseColor("#FF3B30")
            )
            categoryTextView.text = item.category
            dateTextView.text = item.date
        }
    }
}