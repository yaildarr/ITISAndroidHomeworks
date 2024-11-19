package projects.com.hw2_recyclerview.Holder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import projects.com.hw2_recyclerview.Model.ImageTextHoldersData
import projects.com.hw2_recyclerview.Model.MultiHoldersData
import projects.com.hw2_recyclerview.databinding.ItemListBinding

class ItemListHolder(
    val binding: ItemListBinding,
    private val requestManager: RequestManager,
    private val action : (Int) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener{
            action.invoke(adapterPosition)
        }
    }

    fun bindItem(item: ImageTextHoldersData){
        with(binding){
            text.text = item.title
            requestManager.load(item.imageUrl)
                .into(imageView)
        }
    }
}