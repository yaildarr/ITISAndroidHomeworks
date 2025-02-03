package projects.com.hw4_themesnotifications.repository

import projects.com.hw4_themesnotifications.R
import projects.com.hw4_themesnotifications.model.Theme

class ThemeRepository {
    val themesList: List<Theme> = listOf(
        Theme(
            id = 1,
            color = R.color.redButton
        ),
        Theme(
            id = 2,
            color = R.color.greenButton
        ),
        Theme(
            id = 3,
            color = R.color.yellowButton
        )
    )
}