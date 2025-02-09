package com.example.hw6_room.fragment

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hw6_room.R
import com.example.hw6_room.adapter.TransactionAdapter
import com.example.hw6_room.base.BaseActivity
import com.example.hw6_room.data.entities.TransactionEntity
import com.example.hw6_room.databinding.FragmentHomeBinding
import com.example.hw6_room.di.ServiceLocator
import com.example.hw6_room.util.AuthPreferences
import com.example.hw6_room.util.NavigationAction
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class HomeFragment(
    private val userId: Long
) : Fragment(R.layout.fragment_home) {

    private val binding : FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)

    private var adapter : TransactionAdapter? = null

    private var transactionRepository = ServiceLocator.getTransactionRepository()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        bottomSheet()
        logout()
        setupSwipeToDelete()
    }

    private fun initRecyclerView(){
        with(binding){
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        loadTransactions()
    }

    private fun loadTransactions() {
        lifecycleScope.launch {
            val transactions = transactionRepository.getTransactionsByUserId(userId.toString())
            if (transactions != null) {
                adapter = TransactionAdapter(transactions)
                binding.recyclerView.adapter = adapter
                adapter?.notifyDataSetChanged()
                println("Загружено ${transactions.size} транзакций")
            } else {
                Toast.makeText(context, "Нет транзакций", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun bottomSheet(){
        binding.floatingBtn.setOnClickListener {
            BottomSheetFragment(
                userId = userId,
                onAddTransaction = { transaction ->
                    lifecycleScope.launch {
                        addTransaction(transaction)
                        loadTransactions()
                    }
                }
            ).show(parentFragmentManager,"bottom_sheet")
        }
    }

    private suspend fun addTransaction(transactionEntity: TransactionEntity){
        coroutineScope {
            launch {
                transactionRepository.saveTransaction(transactionEntity)
            }
        }
    }

    private fun logout() {
        with(binding){
            buttonLogout.setOnClickListener{
                AuthPreferences.clearUserId(requireContext())
                (requireActivity() as? BaseActivity)?.navigate(
                    destination = LoginFragment(),
                    destinationTag = "loginFragment",
                    action = NavigationAction.REPLACE,
                    isAddToBackStack = false,
                    isUseAnimation = true
                )
            }
        }
    }

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
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val transaction = adapter?.getItemAtPosition(position) ?: return

                lifecycleScope.launch {
                    val result = transactionRepository.deleteTransactionById(transaction.id.toString())
                    if (result > 0) {
                        requireActivity().runOnUiThread {
                            loadTransactions()
                            Toast.makeText(requireContext(), "Транзакция удалена", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Ошибка удаления", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                return 0.66f
            }
        }

        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.recyclerView)
    }
}