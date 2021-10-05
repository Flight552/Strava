package com.ybrflight552.fitnessapp.utils.locale

import android.content.Context
import android.content.res.Configuration
import java.util.*

object ChangeLanguage {
    fun changeLanguage(language: String, context: Context) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }
}