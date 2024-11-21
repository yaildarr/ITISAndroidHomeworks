package projects.com.hw2_recyclerview.util

import androidx.recyclerview.widget.DiffUtil
import projects.com.hw2_recyclerview.Holder.ItemListHolder
import projects.com.hw2_recyclerview.Model.ImageTextHoldersData
import projects.com.hw2_recyclerview.Model.MultiHoldersData

class MultiDiffUtilCallback(
    private val oldList: List<MultiHoldersData>,
    private val newList: List<MultiHoldersData>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if(oldList[oldItemPosition] is ImageTextHoldersData && newList[newItemPosition] is ImageTextHoldersData){
            oldList[oldItemPosition] == newList[newItemPosition]
        } else {
            false
        }
    }
}