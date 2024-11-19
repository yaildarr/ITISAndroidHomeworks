package projects.com.hw2_recyclerview.Fragment

import android.icu.text.Transliterator.Position
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import projects.com.hw2_recyclerview.Adapter.MainAdapter
import projects.com.hw2_recyclerview.Model.MultiHoldersData
import projects.com.hw2_recyclerview.Model.MyListItem
import projects.com.hw2_recyclerview.R
import projects.com.hw2_recyclerview.Repository.ItemRepository
import projects.com.hw2_recyclerview.databinding.FragmentHomeBinding


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null

    private var rvAdapter: MainAdapter? = null

    private var dataList = mutableListOf<MultiHoldersData>()

    private var isGrid = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        if (dataList.isEmpty()) {
            dataList.addAll(ItemRepository().getList())
        }
        rvAdapter = MainAdapter(
            requestManager = Glide.with(this),
            items = dataList,
            onViewChange = { isGridView ->
                isGrid = isGridView
                updateLayoutManager()
            },
            listAction = { position ->
                val clickedItem = dataList[position]
                Toast.makeText(requireActivity(), "Вы нажали: ${clickedItem.id}", Toast.LENGTH_SHORT).show()
            }
        )
    }



    private fun updateLayoutManager() {
        binding?.recyclerView?.layoutManager =
            if (!isGrid) GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            else LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }
}