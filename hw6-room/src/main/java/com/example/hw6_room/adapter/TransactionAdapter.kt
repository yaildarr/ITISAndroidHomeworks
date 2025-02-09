package com.example.hw6_room.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw6_room.data.entities.TransactionEntity
import com.example.hw6_room.databinding.ItemListBinding
import com.example.hw6_room.holder.TransactionHolder

class TransactionAdapter(
    private val transactions: List<TransactionEntity>
): RecyclerView.Adapter<TransactionHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        return TransactionHolder(
            binding = ItemListBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        holder.bindItem(transactions[position])
    }

    fun getItemAtPosition(position: Int): TransactionEntity? {
        return if (position in transactions.indices) transactions[position] else null
    }


}