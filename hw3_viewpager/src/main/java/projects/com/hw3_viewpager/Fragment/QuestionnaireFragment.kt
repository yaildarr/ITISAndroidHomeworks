package projects.com.hw3_viewpager.Fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import projects.com.hw3_viewpager.Adapter.RecycleAdapter
import projects.com.hw3_viewpager.Model.Question
import projects.com.hw3_viewpager.R
import projects.com.hw3_viewpager.Repository.QuestionRepository
import projects.com.hw3_viewpager.databinding.FragmentQuestionnaireBinding

class QuestionnaireFragment() : Fragment(R.layout.fragment_questionnaire) {

    private val binding : FragmentQuestionnaireBinding by viewBinding(FragmentQuestionnaireBinding::bind)

    private var question : Question? = null

    private var adapter : RecycleAdapter? = null

    private val repository = QuestionRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getQuestion()
        initRecycleView()
    }


    private fun getQuestion(){
        val id = arguments?.getInt(questionId)
        question = id?.let {
            repository.findById(it)
        }
        if (question != null) {
            Log.d("myLog",question.toString())
            binding.questionCount.text = question!!.text
        }
    }

    private fun initRecycleView(){
        Log.d("myLog","initRecycleView ${question?.itemsAnswer}")
        if (question != null) {
            adapter = RecycleAdapter(
                question!!,
            )
        }
        with(binding){
            recyclerQuest.adapter = adapter
            recyclerQuest.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        }
    }


    companion object{
        fun newInstance(bundle: Bundle) : Fragment{
            return QuestionnaireFragment().apply {
                arguments = bundle
            }
        }
        val questionId = "QuestionId"
    }
}