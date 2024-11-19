package projects.com.hw2_recyclerview.Fragment

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import projects.com.hw2_recyclerview.R
import projects.com.hw2_recyclerview.databinding.FragmentBottomSheetBinding
import projects.com.hw2_recyclerview.util.ActionType

class BottomSheetFragment() : BottomSheetDialogFragment(R.layout.fragment_bottom_sheet) {

    private val binding: FragmentBottomSheetBinding by viewBinding(FragmentBottomSheetBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnAddRandom.setOnClickListener { sendResult(action = ActionType.ADD_RANDOM)}
            btnRemoveRandom.setOnClickListener { sendResult(action = ActionType.REMOVE_RANDOM)}
            btnAddSingle.setOnClickListener { sendResult(action = ActionType.ADD_ONE)}
            btnRemoveSingle.setOnClickListener { sendResult(action = ActionType.REMOVE_ONE)}
        }
    }

    fun sendResult(action: ActionType) {
        with(binding) {
            val inputText = editTextCount.text.toString()
            val count = inputText.toIntOrNull()

            when (action) {
                ActionType.ADD_RANDOM, ActionType.REMOVE_RANDOM -> {
                    if (count == null || inputText.isBlank()) {
                        editTextCount.error = "Введите число"
                        return
                    }
                }

                ActionType.ADD_ONE, ActionType.REMOVE_ONE -> {}
            }

            parentFragmentManager.setFragmentResult(
                ARG_NUMBER,
                Bundle().apply {
                    putInt(ARG_NUMBER, count ?: 0)
                    putString(ARG_ACTION, action.toString())
                }
            )
            dismiss()
        }
    }

    companion object {
        const val ARG_NUMBER = "ARG_NUMBER"
        const val ARG_ACTION = "ARG_ACTION"
    }
}