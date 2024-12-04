package projects.com.hw3_viewpager.Repository

import projects.com.hw3_viewpager.Model.Question
import projects.com.hw3_viewpager.Model.Option

object QuestionRepository {
    val questions =  listOf(
        Question(
            id = 0,
            text = "Какую кухню вы готовите дома чаще всего?",
            itemsAnswer = mutableListOf(
                Option(1, "Домашнюю"),
                Option(2, "Итальянскую"),
                Option(3, "Японскую"),
                Option(4, "Азиатскую")
            ),
            isAnswered = false
        ),
        Question(
            id = 1,
            text = "Какое ваше любимое время года?",
            itemsAnswer = mutableListOf(
                Option(1, "Зима"),
                Option(2, "Весна"),
                Option(3, "Лето"),
                Option(4, "Осень")
            ),
            isAnswered = false
        ),
        Question(
            id = 2,
            text = "Какой жанр фильмов вам нравится больше всего?",
            itemsAnswer = mutableListOf(
                Option(1, "Комедия"),
                Option(2, "Драма"),
                Option(3, "Боевик"),
                Option(4, "Ужасы")
            ),
            isAnswered = false
        ),
        Question(
            id = 3,
            text = "Какой напиток вы предпочитаете?",
            itemsAnswer = mutableListOf(
                Option(1, "Кофе"),
                Option(2, "Чай"),
                Option(3, "Сок"),
                Option(4, "Вода")
            ),
            isAnswered = false
        ),
        Question(
            id = 4,
            text = "Какой вид спорта вам интересен?",
            itemsAnswer = mutableListOf(
                Option(1, "Футбол"),
                Option(2, "Баскетбол"),
                Option(3, "Теннис"),
                Option(4, "Бег")
            ),
            isAnswered = false
        ),
        Question(
            id = 5,
            text = "Какая ваша любимая кухня?",
            itemsAnswer = mutableListOf(
                Option(1, "Итальянская"),
                Option(2, "Японская"),
                Option(3, "Мексиканская"),
                Option(4, "Французская")
            ),
            isAnswered = false
        ),
        Question(
            id = 6,
            text = "Какой жанр музыки вы слушаете?",
            itemsAnswer = mutableListOf(
                Option(1, "Поп"),
                Option(2, "Рок"),
                Option(3, "Классика"),
                Option(4, "Хип-хоп")
            ),
            isAnswered = false
        ),
        Question(
            id = 7,
            text = "Как вы предпочитаете проводить отпуск?",
            itemsAnswer = mutableListOf(
                Option(1, "На пляже"),
                Option(2, "В горах"),
                Option(3, "В городе"),
                Option(4, "На природе")
            ),
            isAnswered = false
        ),
        Question(
            id = 8,
            text = "Какое ваше любимое животное?",
            itemsAnswer = mutableListOf(
                Option(1, "Собака"),
                Option(2, "Кошка"),
                Option(3, "Лошадь"),
                Option(4, "Попугай")
            ),
            isAnswered = false
        ),
        Question(
            id = 9,
            text = "Какой ваш любимый десерт?",
            itemsAnswer = mutableListOf(
                Option(1, "Торт"),
                Option(2, "Мороженое"),
                Option(3, "Пирожное"),
                Option(4, "Фрукты")
            ),
            isAnswered = false
        ),
        Question(
            id = 10,
            text = "Какую суперспособность вы бы хотели иметь?",
            itemsAnswer = mutableListOf(
                Option(1, "Полёт"),
                Option(2, "Невидимость"),
                Option(3, "Суперсила"),
                Option(4, "Телепортация")
            ),
            isAnswered = false
        ),
        Question(
            id = 11,
            text = "Какая ваша любимая книга?",
            itemsAnswer = mutableListOf(
                Option(1, "Фантастика"),
                Option(2, "Роман"),
                Option(3, "Детектив"),
                Option(4, "Биография")
            ),
            isAnswered = false
        ),
        Question(
            id = 12,
            text = "Какой ваш любимый цвет?",
            itemsAnswer = mutableListOf(
                Option(1, "Красный"),
                Option(2, "Синий"),
                Option(3, "Зелёный"),
                Option(4, "Жёлтый")
            ),
            isAnswered = false
        ),
        Question(
            id = 13,
            text = "Как вы предпочитаете начинать день?",
            itemsAnswer = mutableListOf(
                Option(1, "Завтрак"),
                Option(2, "Спорт"),
                Option(3, "Работа"),
                Option(4, "Чтение")
            ),
            isAnswered = false
        ),
        Question(
            id = 14,
            text = "Какой ваш любимый вид транспорта?",
            itemsAnswer = mutableListOf(
                Option(1, "Автомобиль"),
                Option(2, "Велосипед"),
                Option(3, "Поезд"),
                Option(4, "Самолёт")
            ),
            isAnswered = false
        ),
    )
    fun findById(id: Int): Question? {
        return questions.find { it.id == id }
    }
}