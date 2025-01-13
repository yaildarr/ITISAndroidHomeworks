package projects.com.hw4_themesnotifications.repository

import android.app.NotificationManager
import projects.com.hw4_themesnotifications.model.NotificationChannelData

class NotificationChannels{
    val notificationsChannels = listOf(
        NotificationChannelData("low_channel_id", "LOW", NotificationManager.IMPORTANCE_LOW),
        NotificationChannelData("default_channel_id", "DEFAULT", NotificationManager.IMPORTANCE_DEFAULT),
        NotificationChannelData("high_channel_id", "HIGH", NotificationManager.IMPORTANCE_HIGH),
        NotificationChannelData("max_channel_id", "MAX", NotificationManager.IMPORTANCE_MAX),
    )
}