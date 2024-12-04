package projects.com.hw3_viewpager.Model

data class Question(
    val id: Int,
    val text: String,
    val itemsAnswer: List<Option>,
    var selectedAnswer : Int = -1,
    var isAnswered : Boolean = false
)