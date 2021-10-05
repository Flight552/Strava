package com.ybrflight552.fitnessapp.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ybrflight552.fitnessapp.utils.AuthInfo

object AppPermissions {
    fun checkContactPermissions(context: Context): Boolean {
        return (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED)
    }

    fun requestContactPermissions(activity: Activity) {
        val permissions: Array<String> =
            arrayOf(Manifest.permission.SEND_SMS)
        ActivityCompat.requestPermissions(activity, permissions, AuthInfo.CONTACT_PERMISSIONS_CODE)
    }
}