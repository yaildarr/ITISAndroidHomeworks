package projects.com.hw4_themesnotifications.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import projects.com.hw4_themesnotifications.databinding.ItemThemeBinding
import projects.com.hw4_themesnotifications.holder.ThemeHolder
import projects.com.hw4_themesnotifications.model.Theme


class ThemeAdapter(
    private val items: List<Theme>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ThemeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeHolder {
        return ThemeHolder(
            binding = ItemThemeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ThemeHolder, position: Int) {
        holder.bindItem(items[position])
    }
}
