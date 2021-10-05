package com.ybrflight552.fitnessapp.ui.onboarding

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.ybrflight552.fitnessapp.utils.AuthInfo

object File {
    fun getOnBoardingInfo(context: Context) : Boolean {
        var isSeen = false
        context.getSharedPreferences(
            AuthInfo.ONBOARDING_PREF_NAME,
            AppCompatActivity.MODE_PRIVATE
        ).apply {
             isSeen = getBoolean(AuthInfo.ONBOARDING_PREF_KEY, false)
        }
        return isSeen
    }

    fun saveSharedLanguage(context: Context, lang: String) {

        context.getSharedPreferences(AuthInfo.SHARED_TOKEN_NAME, AppCompatActivity.MODE_PRIVATE).apply {
            edit().putString(AuthInfo.SHARED_LANGUAGE_KEY, lang).apply()
        }
    }
}