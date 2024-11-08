package itis.android.hw1_fragments

import android.app.Notification.Action
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import itis.android.hw1_fragments.databinding.DialogBottomSheetBinding

class BottomSheetFragment : BottomSheetDialogFragment(R.layout.dialog_bottom_sheet) {


    lateinit var viewBinding: DialogBottomSheetBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = DialogBottomSheetBinding.bind(view)
        viewBinding.ActionBtnBS.isEnabled = false

        initViews()
    }

    private fun initViews(){
        val bundle = Bundle()
        with(viewBinding){
            textInputLayoutBS.editText?.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().isEmpty()){
                        ActionBtnBS.setEnabled(false)
                    } else {
                        ActionBtnBS.setEnabled(true)
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })
            ActionBtnBS.setOnClickListener {
                textInputLayoutBS.editText?.text.let {
                    bundle.putString(textInputStringFromBS,it.toString())
                    dismiss()
                    (requireParentFragment() as? FirstFragment)?.navigateBack(bundle)
                }
            }
        }
    }
    companion object{
        const val textInputString = "textInput"
        const val firstFragmentTag = "FirstFragment"
        const val textInputStringFromBS = "textInputBS"
    }
}