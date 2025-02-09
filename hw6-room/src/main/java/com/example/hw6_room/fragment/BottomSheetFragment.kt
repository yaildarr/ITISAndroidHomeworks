package com.example.hw6_room.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.hw6_room.R
import com.example.hw6_room.data.entities.TransactionEntity
import com.example.hw6_room.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch

class BottomSheetFragment(
    private val onAddTransaction: suspend (TransactionEntity) -> Unit,
    private val userId: Long
): BottomSheetDialogFragment(R.layout.fragment_bottom_sheet){

    private val binding: FragmentBottomSheetBinding by viewBinding(FragmentBottomSheetBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categories = listOf(getString(R.string.income), getString(R.string.consumption))
        val categoryAdapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_menu_popup_item,
            categories
        )
        binding.categoryAutoCompleteTextView.setAdapter(categoryAdapter)

        binding.categoryAutoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            val selectedCategory = parent.getItemAtPosition(position).toString()
            binding.categoryAutoCompleteTextView.setText(selectedCategory, false)

        }

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val amount = binding.amountEditText.text.toString().toDoubleOrNull() ?: 0.0
            val category = binding.categoryAutoCompleteTextView.text.toString()


            if (title.isNotEmpty() && amount != 0.0 && category.isNotEmpty()) {
                lifecycleScope.launch{
                    saveTransaction(title, amount, category)
                }
                dismiss()
            } else {
                Toast.makeText(context,
                    getString(R.string.Fill_in_all_required_fields), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private suspend fun saveTransaction(title: String, amount: Double, category: String) {
        Toast.makeText(context, getString(R.string.transaction_saved), Toast.LENGTH_SHORT).show()
        onAddTransaction.invoke(TransactionEntity(
            userId = userId.toString(),
            title = title,
            amount = amount.toString(),
            category = category)
        )
    }

}