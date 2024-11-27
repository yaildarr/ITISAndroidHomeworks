package projects.com.hw3_viewpager.Adapter

import android.graphics.Path.Op
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import projects.com.hw3_viewpager.Holder.AnswerHolder
import projects.com.hw3_viewpager.Model.Option
import projects.com.hw3_viewpager.Model.Question
import projects.com.hw3_viewpager.Repository.QuestionRepository
import projects.com.hw3_viewpager.databinding.RecycleItemBinding

class RecycleAdapter(
    val question: Question,
) : RecyclerView.Adapter<AnswerHolder>() {

    private var repository = QuestionRepository

    private var selectedPosition = question.selectedAnswer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerHolder {
        Log.d("myLog","onCreateViewHolder")
        return AnswerHolder(
            binding = RecycleItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        Log.d("myLog","getItemCount")
        return question.itemsAnswer.size
    }

    override fun onBindViewHolder(holder: AnswerHolder, position: Int) {
        Log.d("myLog","onBindViewHolder")
        val item = question.itemsAnswer[position]
        Log.d("myLog",item.toString() + "onBindViewHolder")
        holder.bindItem(item,position == selectedPosition
        ) {
            if (selectedPosition != position) {
                notifyItemChanged(selectedPosition)
                selectedPosition = holder.bindingAdapterPosition
                question.selectedAnswer = holder.bindingAdapterPosition
                notifyItemChanged(selectedPosition)
                repository.findById(position)?.isAnswered = true
            }
        }
    }
}