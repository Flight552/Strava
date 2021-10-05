package com.ybrflight552.fitnessapp.utils.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import java.util.*

object AlarmManagerObject {

    private var alarmManager: AlarmManager? = null
    private lateinit var intentPending: PendingIntent

    fun createAlarm(context: Context) {
        val receiver = ComponentName(context, ServiceManager::class.java)
        context.packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

    }

    fun createAlarmManager(context: Context) {

        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        intentPending = Intent(context, ServiceManager::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 12)
            set(Calendar.MINUTE, 1)
        }


        //------------ работает
        alarmManager?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            intentPending
        )


    }

}