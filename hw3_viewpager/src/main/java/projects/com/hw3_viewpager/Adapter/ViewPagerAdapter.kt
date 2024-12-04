package projects.com.hw3_viewpager.Adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import projects.com.hw3_viewpager.Fragment.QuestionnaireFragment
import projects.com.hw3_viewpager.Model.Question
import projects.com.hw3_viewpager.Repository.QuestionRepository

class ViewPagerAdapter(
    manager: FragmentManager,
    lifecycle: Lifecycle,
    val items: List<Question>
) : FragmentStateAdapter(manager,lifecycle) {

    private val repository = QuestionRepository

    private val bundle = Bundle()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        repository.findById(position)
            ?.let { bundle.putInt(QuestionnaireFragment.questionId, items[position].id) }
        return QuestionnaireFragment.newInstance(bundle)
    }
}