package projects.com.hw2_recyclerview.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import projects.com.hw2_recyclerview.Holder.ButtonHolder
import projects.com.hw2_recyclerview.Holder.ItemListHolder
import projects.com.hw2_recyclerview.Model.ButtonsHolderData
import projects.com.hw2_recyclerview.Model.ImageTextHoldersData
import projects.com.hw2_recyclerview.Model.MultiHoldersData
import projects.com.hw2_recyclerview.databinding.ItemListBinding
import projects.com.hw2_recyclerview.databinding.LineButtonBinding

class MainAdapter(
    private val requestManager: RequestManager,
    items: List<MultiHoldersData>,
    private val onViewChange : (Boolean) -> Unit,
    private val listAction: (Int) -> Unit,
    private val onItemLongClick: (Int) -> Unit,

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_BUTTON = 0
    private val TYPE_ITEM = 1

    private val dataList = mutableListOf<MultiHoldersData>()

    init {
        dataList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_BUTTON -> {
                ButtonHolder(
                    binding = LineButtonBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_ITEM -> {
                ItemListHolder(
                    binding = ItemListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    requestManager = requestManager,
                    action = listAction
                )
            }

            else -> throw IllegalStateException("Unknown holder")
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ButtonHolder -> {
                holder.bindButton(onViewChange)
            }
            is ItemListHolder -> {
                val item = dataList[position]
                if (item is ImageTextHoldersData) {
                    holder.bindItem(item)
                } else {
                    throw IllegalStateException("Unexpected data type for ItemListHolder: $item")
                }
            }
            else -> Unit
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClick(position)
            true
        }
    }



    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_BUTTON
        } else {
            when (dataList[position]) {
                is ImageTextHoldersData -> TYPE_ITEM
                is ButtonsHolderData -> TYPE_BUTTON
                else -> throw IllegalStateException("Unknown data type at position: $position")
            }
        }
    }

    fun updateItems(newItems: List<MultiHoldersData>) {
        dataList.clear()
        dataList.addAll(newItems)
        notifyDataSetChanged()
        Log.d("MainAdapter", "Adapter updated with ${dataList.size} items")
    }



}