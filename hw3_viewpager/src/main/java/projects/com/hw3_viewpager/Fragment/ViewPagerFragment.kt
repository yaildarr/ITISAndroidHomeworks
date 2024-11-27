package projects.com.hw3_viewpager.Fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import projects.com.hw3_viewpager.Adapter.ViewPagerAdapter

import projects.com.hw3_viewpager.R
import projects.com.hw3_viewpager.Repository.QuestionRepository
import projects.com.hw3_viewpager.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {

    private val binding: FragmentViewPagerBinding by viewBinding(FragmentViewPagerBinding::bind)

    private var adapter: ViewPagerAdapter? = null

    private var repository = QuestionRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        onPageChangeListener()
        updateButtonState()
    }

    private fun init(){
        adapter = ViewPagerAdapter(
            parentFragmentManager,
            this.lifecycle,
            repository.questions
        )
        with(binding) {
            viewPager.adapter = adapter
            buttonForward.setOnClickListener {
                if (viewPager.currentItem < (viewPager.adapter?.itemCount?.minus(1) ?: 0)
                ) {
                    viewPager.currentItem += 1
                } else {
                    if (isAllQuestionsAnswered()) {
                        Snackbar.make(viewPager, "Тест завершен!", Snackbar.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(viewPager, "Вы ответили не на все вопросы!", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        with(binding) {
            buttonBack.setOnClickListener {
                if (viewPager.currentItem > 0) {
                    viewPager.currentItem -= 1
                }
            }
        }
    }

    private fun onPageChangeListener(){
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updatePageCounter(position)
                updateButtonState()
            }
        })
    }

    private fun updatePageCounter(position: Int){
        val totalPages = adapter?.itemCount ?: 0
        binding.questionCount.text = "${position+1}/${totalPages}"
    }

    @SuppressLint("ResourceType")
    private fun updateButtonState() {
        binding.apply {
            buttonBack.isEnabled = viewPager.currentItem > 0

            if(viewPager.currentItem < (viewPager.adapter?.itemCount?.minus(1) ?: 0)) {
                buttonForward.setBackgroundColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.primary_material_dark))
                buttonBack.setBackgroundColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.primary_material_dark))
                buttonForward.setIconResource(R.drawable.ic_forward)
            }

            else {
                buttonForward.setBackgroundColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.design_default_color_error))
                buttonForward.setIconResource(R.drawable.baseline_check_24)
            }
        }
    }


    private fun isAllQuestionsAnswered(): Boolean {
        val questions = repository.questions // Получение списка вопросов
        return questions.all { it.selectedAnswer != -1 }
    }
}