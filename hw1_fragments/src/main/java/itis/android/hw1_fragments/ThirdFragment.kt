package itis.android.hw1_fragments

import android.media.ThumbnailUtils
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import itis.android.hw1_fragments.databinding.FragmentThirdBinding

class ThirdFragment : Fragment(R.layout.fragment_third) {

    private lateinit var viewBinding: FragmentThirdBinding

    private var value = textInputString

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        value = if (!arguments?.getString(textInputString).isNullOrEmpty()) {
            arguments?.getString(textInputString).toString()
        } else {
            defaultTextString
        }
        viewBinding = FragmentThirdBinding.bind(view)
        with (viewBinding){
            value.let{
                textOutThird.text = it
            }
        }
    }

    companion object{
        fun getInstance(bundle: Bundle) : ThirdFragment{
            return ThirdFragment().apply {
                arguments = bundle
            }
        }
        val defaultTextString = "Стандартный текст"
        val textInputString = "textInput"
    }
}