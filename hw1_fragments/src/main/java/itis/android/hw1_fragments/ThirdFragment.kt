package itis.android.hw1_fragments

import android.media.ThumbnailUtils
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import itis.android.hw1_fragments.databinding.FragmentThirdBinding

class ThirdFragment : Fragment(R.layout.fragment_third) {

    private lateinit var viewBinding: FragmentThirdBinding

    private var value = "Стандартный текст"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        value = if (!arguments?.getString("textInput").isNullOrEmpty()) {
            arguments?.getString("textInput").toString()
        } else {
            "Стандартный текст"
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
    }
}