package projects.com.hw2_recyclerview.Repository

import projects.com.hw2_recyclerview.Model.ImageTextHoldersData

class AdditionalyRepository {
    val movieList = listOf(
        ImageTextHoldersData(
            id = 2,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/5/53/The_Lord_of_the_Rings._The_Return_of_the_King_%E2%80%94_movie.jpg",
            title = "Властелин колец: Возвращение короля",
            description = "Заключительная часть трилогии о борьбе за Средиземье."
        ),
        ImageTextHoldersData(
            id = 3,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/a/a1/Black_Widow_logo.jpg",
            title = "Черная вдова",
            description = "Приключения Наташи Романофф после событий 'Мстителей'."
        ),
        ImageTextHoldersData(
            id = 5,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/thumb/b/b2/Deadpool_film.jpg/640px-Deadpool_film.jpg",
            title = "Дэдпул",
            description = "Комедийный супергеройский фильм о наемнике с необычными способностями."
        ),
        ImageTextHoldersData(
            id = 6,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/9/95/The_Silence_Of_The_Lambs.jpg",
            title = "Молчание ягнят",
            description = "Триллер о противостоянии агентки ФБР и серийного убийцы."
        ),
        ImageTextHoldersData(
            id = 7,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/thumb/f/f7/Matrix_Revolutions_poster.jpg/640px-Matrix_Revolutions_poster.jpg",
            title = "Матрица: Революция",
            description = "Заключительная часть трилогии о борьбе человечества с машинами."
        )
    )
    fun getRandom(count: Int): List<ImageTextHoldersData> {
        if (movieList.isEmpty()) return emptyList()

        val result = mutableListOf<ImageTextHoldersData>()
        while (result.size < count) {
            result.addAll(movieList.shuffled()) // Перемешиваем и добавляем элементы
        }
        return result.take(count) // Обрезаем до нужного количества
    }

}