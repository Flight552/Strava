package com.ybrflight552.fitnessapp.utils.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

object NotificationChannels {

    fun create(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createMessageChannel(context)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createMessageChannel(context: Context) {
        val name = "workout"
        val channelDescription = "workout alarm"
        val priority = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(NOTIFICATION_CHANNEL, name, priority)
            .apply {
                description = channelDescription
                setSound(
                    android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION),
                    null
                )
            }
        NotificationManagerCompat.from(context)
            .createNotificationChannel(channel)
    }

    const val NOTIFICATION_CHANNEL = "12345"
    const val NOTIFICATION_ID = 12345
}