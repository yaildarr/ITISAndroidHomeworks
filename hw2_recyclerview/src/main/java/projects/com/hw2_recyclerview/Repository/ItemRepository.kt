package projects.com.hw2_recyclerview.Repository

import android.content.Context
import projects.com.hw2_recyclerview.Model.ButtonsHolderData
import projects.com.hw2_recyclerview.Model.ImageTextHoldersData
import projects.com.hw2_recyclerview.Model.MultiHoldersData
import projects.com.hw2_recyclerview.Model.MyListItem

class ItemRepository {
    fun getList() : List<MultiHoldersData> = listOf(
        ButtonsHolderData(
            id = 0,
            btn1text = "Верт",
            btn2text = "Сетка"
        ),
        ImageTextHoldersData(
            id = 1,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/thumb/1/19/Titanic_%28Official_Film_Poster%29.png/209px-Titanic_%28Official_Film_Poster%29.png",
            title = "Титаник",
            description = "История любви между Розой и Джеком на фоне трагедии затопления лайнера Титаник."
        ),
        ImageTextHoldersData(
            id = 2,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/9/9d/Matrix-DVD.jpg",
            title = "Матрица",
            description = "Классический sci-fi фильм о реальности и свободе в мире, управляемом машинами."
        ),
        ImageTextHoldersData(
            id = 3,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/b/bc/Poster_Inception_film_2010.jpg",
            title = "Начало",
            description = "Фильм о ворах, которые проникают в сны людей для кражи секретов."
        ),
        ImageTextHoldersData(
            id = 4,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/7/79/Pirates-of-the-Caribbean-The-Curse-of-the-Black-Pearl-.jpg",
            title = "Пираты Карибского моря: Проклятие Черной жемчужины",
            description = "Приключения капитана Джека Воробья и его команды на фоне пиратских интриг."
        ),
        ImageTextHoldersData(
            id = 5,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/b/b0/Green_mile_film.jpg",
            title = "Зеленая миля",
            description = "Драма о тюремном надзирателе и заключенном с необычными способностями."
        ),
        ImageTextHoldersData(
            id = 6,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/c/c3/Interstellar_2014.jpg",
            title = "Интерстеллар",
            description = "Научно-фантастический фильм о путешествии через червоточину в поисках нового дома для человечества."
        ),
        ImageTextHoldersData(
            id = 7,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/thumb/d/de/%D0%A4%D0%BE%D1%80%D1%80%D0%B5%D1%81%D1%82_%D0%93%D0%B0%D0%BC%D0%BF.jpg/227px-%D0%A4%D0%BE%D1%80%D1%80%D0%B5%D1%81%D1%82_%D0%93%D0%B0%D0%BC%D0%BF.jpg",
            title = "Форрест Гамп",
            description = "История жизни человека с низким IQ, который стал свидетелем многих исторических событий."
        ),
        ImageTextHoldersData(
            id = 8,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/thumb/e/ef/The_Shining.jpg/220px-The_Shining.jpg",
            title = "Сияние",
            description = "Психологический хоррор о писателе, который теряет рассудок в заброшенном отеле."
        ),
        ImageTextHoldersData(
            id = 9,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/b/b4/Harry_Potter_and_the_Philosopher%27s_Stone_%E2%80%94_movie.jpg",
            title = "Гарри Поттер и философский камень",
            description = "Приключения молодого волшебника Гарри Поттера в школе магии Хогвартс."
        ),
        ImageTextHoldersData(
            id = 10,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/b/b6/%D0%9A%D1%80%D0%B5%D1%81%D1%82%D0%BD%D1%8B%D0%B9_%D0%BE%D1%82%D0%B5%D1%86_%D1%80%D0%BE%D0%BC%D0%B0%D0%BD_SIGNET.jpg",
            title = "Крестный отец",
            description = "Эпопея о семье мафии Корлеоне и их влиянии на преступный мир."
        ),
        ImageTextHoldersData(
            id = 11,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/b/b9/Knockin%27_On_Heaven%27s_Door.jpg",
            title = "Достучаться до небес",
            description = "Два друга решают осуществить свои мечты, зная, что у них осталось немного времени."
        ),
        ImageTextHoldersData(
            id = 12,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/f/f4/%D0%A2%D1%91%D0%BC%D0%BD%D1%8B%D0%B9_%D1%80%D1%8B%D1%86%D0%B0%D1%80%D1%8C_%282008%29_%D0%BF%D0%BE%D1%81%D1%82%D0%B5%D1%80.jpg",
            title = "Темный рыцарь",
            description = "Баттл Бэтмена с Джокером, который угрожает городу Готэм."
        ),
        ImageTextHoldersData(
            id = 13,
            imageUrl = "https://play-lh.googleusercontent.com/3babVIlKZ-0RxlpRPUH5W51cDon1zQvyCyywcbaQRo3g3b9ylcnMgKDWpoRPS7zO1xlh",
            title = "В поисках счастья",
            description = "История о борьбе за лучшее будущее для себя и своего сына."
        ),
        ImageTextHoldersData(
            id = 14,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/8/86/%D0%A1%D1%83%D0%BF%D0%B5%D1%80%D1%81%D0%B5%D0%BC%D0%B5%D0%B9%D0%BA%D0%B0_Pixar.jpg",
            title = "Суперсемейка",
            description = "Семья супергероев, которая пытается жить обычной жизнью, но сталкивается с угрозой."
        ),
        ImageTextHoldersData(
            id = 15,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/e/e4/La_La_Land.jpg",
            title = "Ла-Ла Ленд",
            description = "Мюзикл о любви между джазовым музыкантом и актрисой в Лос-Анджелесе."
        ),
        ImageTextHoldersData(
            id = 16,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/e/e7/Jurassic_Park_poster.jpg",
            title = "Парк Юрского периода",
            description = "Фильм о парке с клонированными динозаврами, который выходит из-под контроля."
        ),
        ImageTextHoldersData(
            id = 17,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/thumb/6/62/Lion_king_ver1.jpg/274px-Lion_king_ver1.jpg",
            title = "Король Лев",
            description = "Анимационная история о взрослении львенка Симбы и его пути к короне."
        ),
        ImageTextHoldersData(
            id = 18,
            imageUrl = "https://ir-3.ozone.ru/s3/multimedia-g/c1000/6007601488.jpg",
            title = "Звёздные войны: Эпизод IV - Новая надежда",
            description = "Приключения Люка Скайуокера в борьбе с Империей."
        ),
        ImageTextHoldersData(
            id = 19,
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/b/b3/%D0%90%D0%B2%D0%B0%D1%82%D0%B0%D1%80_%D0%9F%D1%83%D1%82%D1%8C_%D0%B2%D0%BE%D0%B4%D1%8B_%D0%BF%D0%BE%D1%81%D1%82%D0%B5%D1%80.jpg",
            title = "Аватар",
            description = "Научно-фантастический фильм о столкновении людей с инопланетной цивилизацией на планете Пандора."
        )
    )
}