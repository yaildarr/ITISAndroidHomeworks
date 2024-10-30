package itis.android.hw1_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import itis.android.hw1_fragments.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {

    private val bundle = Bundle()

    private lateinit var viewBinding: FragmentFirstBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentFirstBinding.bind(view)
        initViews()
    }


    private fun initViews(){
        with(viewBinding){
            ActionBtn2.setOnClickListener{
                (textInputLayout.editText?.text).let {
                    bundle.putString("textInput",it.toString())
                }
                (requireActivity() as? MainActivity)?.navigate(NavigateClasses.SECOND,bundle)
            }
            ActionBtn3.setOnClickListener{
                (textInputLayout.editText?.text).let {
                    bundle.putString("textInput", it.toString())
                }
                (requireActivity() as? MainActivity)?.navigate(NavigateClasses.THIRD,bundle)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        retainInstance = true
    }
}