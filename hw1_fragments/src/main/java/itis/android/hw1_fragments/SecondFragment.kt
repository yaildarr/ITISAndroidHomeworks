package itis.android.hw1_fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import itis.android.hw1_fragments.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {

    private lateinit var viewBinding: FragmentSecondBinding

    private lateinit var value: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        value = if (!arguments?.getString(textInputString).isNullOrEmpty()) {
            arguments?.getString(textInputString).toString()
        } else {
            defaultTextString
        }
        viewBinding = FragmentSecondBinding.bind(view)
        with (viewBinding){
            value.let{
                textOut.text = it
            }
            button3.setOnClickListener{
                (textOut.text).let {
                    bundle.putString(textInputString,it.toString())
                }
                (requireActivity() as? MainActivity)?.apply {
                    navigate(NavigateClasses.FRSECONTDOTHIRD,bundle)
                }

            }
        }
    }
    companion object{
        fun getInstance(bundle: Bundle) : SecondFragment{
            return SecondFragment().apply{
                arguments = bundle
            }
        }
        val defaultTextString = "Стандартный текст"
        val textInputString = "textInput"
    }
}