package itis.android.hw1_fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import itis.android.hw1_fragments.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {

    private val bundle = Bundle()

    val dialog = BottomSheetFragment()

    private lateinit var value: String

    private lateinit var viewBinding: FragmentFirstBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentFirstBinding.bind(view)
        value = if (!arguments?.getString(SecondFragment.textInputString).isNullOrEmpty()) {
            arguments?.getString(SecondFragment.textInputString).toString()
        } else {
            ""
        }
        initViews()
    }


    private fun initViews(){
        with(viewBinding){
            value.let {
                textInputLayout.editText?.setText(it)
            }
            ActionBtn2.setOnClickListener{
                (textInputLayout.editText?.text).let {
                    bundle.putString(textInputString,it.toString())
                }
                (requireActivity() as? MainActivity)?.navigate(NavigateClasses.SECOND,bundle)
            }
            ActionBtn3.setOnClickListener{
                (textInputLayout.editText?.text).let {
                    bundle.putString(textInputString, it.toString())
                }
                (requireActivity() as? MainActivity)?.navigate(NavigateClasses.THIRD,bundle)
            }
            ActionBtn4.setOnClickListener{
                dialog.apply {
                    isCancelable = true
                }
                dialog.show(childFragmentManager,NavigateClasses.BOTTOMSHEET.toString())
            }
        }
    }

    fun navigateBack(bundle: Bundle){
        Log.d("myLog","вызвался метод")
        with(viewBinding){
            value = bundle.getString(textInputStringFromBS).toString()
            textInputLayout.editText?.setText(value)
        }
    }

    companion object{
        fun getInstance(bundle: Bundle): FirstFragment {
            return FirstFragment().apply {
                arguments = bundle
            }
        }
        const val textInputString = "textInput"
        const val firstFragmentTag = "FirstFragmentTag"
        const val defaultTextString = "Стандартный текст"
        const val textInputStringFromBS = "textInputBS"
    }
}