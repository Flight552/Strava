package com.ybrflight552.fitnessapp.internet.network.internet

import android.util.Log
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

// Проверяем наличие интернета при активном интренет соединении
object InternetConnectivity {
    fun execute() : Boolean {
        return try {
            Log.d("InternetConnectivity", "Pinging google")
            val socket = Socket()
            socket.connect(InetSocketAddress(GOOGLE_HOST, GOOGLE_PORT), TIMEOUT)
            socket.close()
            Log.d("InternetConnectivity", "PING success")
            true
        } catch (e: IOException) {
            Log.d("InternetConnectivity", "PING failure")
            false
        }
    }

    private const val GOOGLE_HOST = "8.8.8.8"
    private const val GOOGLE_PORT = 53
    private const val TIMEOUT = 1500
}