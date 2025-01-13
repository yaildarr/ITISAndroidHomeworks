package projects.com.hw4_themesnotifications.handler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import projects.com.hw4_themesnotifications.repository.NotificationChannels

class NotificationHandler {
    fun createNotificationChannels(context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            NotificationChannels().notificationsChannels.forEach{ channelData ->
                val channel = NotificationChannel(
                    channelData.channelId,
                    channelData.name,
                    channelData.importance
                )
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}