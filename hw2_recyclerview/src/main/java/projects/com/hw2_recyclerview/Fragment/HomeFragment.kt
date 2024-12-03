package projects.com.hw2_recyclerview.Fragment

import android.app.AlertDialog
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import projects.com.hw2_recyclerview.Adapter.MainAdapter
import projects.com.hw2_recyclerview.Model.ButtonsHolderData
import projects.com.hw2_recyclerview.Model.ImageTextHoldersData
import projects.com.hw2_recyclerview.Model.MultiHoldersData
import projects.com.hw2_recyclerview.R
import projects.com.hw2_recyclerview.Repository.AdditionalyRepository
import projects.com.hw2_recyclerview.Repository.ItemRepository
import projects.com.hw2_recyclerview.databinding.FragmentHomeBinding
import projects.com.hw2_recyclerview.util.ActionType
import kotlin.random.Random


class HomeFragment : Fragment(R.layout.fragment_home) {


    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)

    private var rvAdapter: MainAdapter? = null

    private var dataList = mutableListOf<MultiHoldersData>()

    val dialog = BottomSheetFragment()

    private var isGrid = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setupSwipeToDelete()
        clickOnFloatingButton()
        selectOnFloatingButton()
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
                updateLayoutManager(isGrid)
            },
            listAction = { position ->
                clickOnItem(position)
            },
            onItemLongClick = { position ->
                showDeleteConfirmationDialog(position)
            }
        )
        binding.apply {
            recyclerView.adapter = rvAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }



    private fun updateLayoutManager(isGrid: Boolean) {
        binding.recyclerView.layoutManager =
            if (!isGrid) {
                GridLayoutManager(context, 3, RecyclerView.VERTICAL, false).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return if (position == 0) 3 else 1
                        }
                    }
                }
            } else {
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            }

        updateItemTouchHelper()
    }

    private fun clickOnFloatingButton(){
        binding.floatingBtn.setOnClickListener{
            dialog.apply {
                isCancelable = true
            }
            dialog.show(parentFragmentManager, bottomSheetTag)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("LayoutManager", isGrid)
    }

    override fun onViewStateRestored(inState: Bundle?) {
        super.onViewStateRestored(inState)
        if (inState != null) {
            if (inState.getBoolean("LayoutManager", false)) {
                updateLayoutManager(isGrid)
            }
        }
    }

    private var itemTouchHelper: ItemTouchHelper? = null

    private fun setupSwipeToDelete() {
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val background = ColorDrawable(Color.RED)
                    background.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    background.draw(c)
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (binding.recyclerView.layoutManager is LinearLayoutManager) {
                    val position = viewHolder.bindingAdapterPosition
                    dataList.removeAt(position)
                    rvAdapter?.updateItems(dataList)
                } else {
                    rvAdapter?.notifyItemChanged(viewHolder.adapterPosition) // Сброс свайпа
                }
            }

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                return 0.66f
            }
        }

        itemTouchHelper = ItemTouchHelper(swipeCallback)
        updateItemTouchHelper()
    }


    private fun showDeleteConfirmationDialog(position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление элемента")
            .setMessage("Вы уверены, что хотите удалить этот элемент?")
            .setPositiveButton("Удалить") { _, _ ->
                dataList.removeAt(position)
                rvAdapter?.updateItems(dataList)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun selectOnFloatingButton(){
        with(binding) {
            parentFragmentManager.setFragmentResultListener(
                ARG_NUMBER,
                this@HomeFragment
            ) { _, bundle ->
                val count = bundle.getInt(BottomSheetFragment.ARG_NUMBER)
                val type_action = bundle.getString(BottomSheetFragment.ARG_ACTION)

                when(type_action) {
                    ActionType.ADD_RANDOM.toString() -> {
                        val newItems = AdditionalyRepository().getRandom(count)
                        newItems.forEach { newItem ->
                            val randomIndex = if (dataList.isEmpty()) 0 else Random.nextInt(1,dataList.size + 1)
                            dataList.add(randomIndex, newItem)
                        }
                        rvAdapter?.updateItems(dataList)
                    }
                    ActionType.REMOVE_RANDOM.toString() -> {
                        val removeCount = minOf(count, dataList.size - 1)
                        Log.d("HomeFragment", "Updating adapter with ${dataList.size} items")
                        repeat(removeCount) {
                            dataList.removeAt(Random.nextInt(1, dataList.size))
                        }
                        rvAdapter?.updateItems(dataList)
                        Log.d("HomeFragment", "DataList size after removal: ${dataList.size}")
                    }
                    ActionType.ADD_ONE.toString() -> {
                        val newItem = AdditionalyRepository().getRandom(1)[0]
                        val randomIndex = if (dataList.isEmpty()) 0 else Random.nextInt(1,dataList.size + 1)
                        dataList.add(randomIndex, newItem)
                        rvAdapter?.updateItems(dataList)
                    }
                    ActionType.REMOVE_ONE.toString() -> {
                        if (dataList.size > 1) {
                            val randomIndex = Random.nextInt(1, dataList.size)
                            dataList.removeAt(randomIndex)
                            rvAdapter?.updateItems(dataList)
                        }
                    }
                    null -> {
                    }
                }

            }
        }
    }

    private fun clickOnItem(position: Int) {
        val fragment = when (val item = dataList[position]) {
            is ImageTextHoldersData -> {
                ItemFragment.newInstance(
                    title = item.title,
                    imageUrl = item.imageUrl,
                    description = item.description
                )
            }
            is ButtonsHolderData -> null
            else -> null
        }

        fragment?.let {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, it)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun updateItemTouchHelper() {
        val currentLayoutManager = binding.recyclerView.layoutManager
        if (currentLayoutManager is LinearLayoutManager) {
            itemTouchHelper?.attachToRecyclerView(binding.recyclerView)
        } else {
            itemTouchHelper?.attachToRecyclerView(null)
        }
    }



    companion object{
        const val bottomSheetTag = "BottomSheet"
        const val ARG_NUMBER = "ARG_NUMBER"
        const val ARG_ACTION = "ARG_ACTION"
    }

}