package com.ybrflight552.fitnessapp.utils

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import androidx.viewbinding.BuildConfig
import com.ybrflight552.fitnessapp.utils.alarm.AlarmManagerObject
import com.ybrflight552.fitnessapp.utils.alarm.NotificationChannels
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FitnessApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AlarmManagerObject.createAlarm(this)
        AlarmManagerObject.createAlarmManager(this)
        NotificationChannels.create(this)
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy( // сообщает о наличии каких-либо ошибок
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads() // проверка возможности чтения с диска
                    .detectDiskWrites() // проверка возможности записи на диска
                    .detectNetwork() // проверка интернет соединения
                    .penaltyDeath() // крашит приложение, если вышеперечисленные не выполняются
                    .build()
            )
            StrictMode.setVmPolicy(
                VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }
    }
}