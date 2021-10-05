package com.ybrflight552.fitnessapp.utils.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ybrflight552.fitnessapp.R
import com.ybrflight552.fitnessapp.utils.AuthInfo
import com.ybrflight552.fitnessapp.utils.locale.ChangeLanguage

class ServiceManager() :
    BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        if (getTimeFromSharedPref(context)) {
            alarmNotification(context)
        }
    }

    private fun alarmNotification(context: Context) {
        val builder = NotificationCompat.Builder(context, NotificationChannels.NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.alam_workout)
            .setContentTitle(context.getString(R.string.workout_notification))
            .setContentText(context.getString(R.string.workout_text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

        NotificationManagerCompat.from(context)
            .notify(NotificationChannels.NOTIFICATION_ID, builder)

    }

    // get time from shared pref
    private fun getTimeFromSharedPref(context: Context): Boolean {
        var isTime: Boolean = true
        context.getSharedPreferences(AuthInfo.SHARED_TOKEN_NAME, AppCompatActivity.MODE_PRIVATE)
            .apply {
                try {
                    val time = getLong(AuthInfo.SHARED_ACTIVITY_TIME, 0L)
                    isTime = if (time > 0) {
                        val totalTime = (System.currentTimeMillis() - time)
                        ((totalTime / 1000) / 3600).toInt() > 24
                    } else {
                        true
                    }
                } catch (e: Exception) {
                    Log.d("ServiceManager", "shared pref error $e")
                }
            }
        return isTime
    }
}