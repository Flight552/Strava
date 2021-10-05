package com.ybrflight552.fitnessapp.utils

import net.openid.appauth.ResponseTypeValues

object AuthInfo {

    var ACCESS_TOKEN: String? = null
    lateinit var REFRESH_TOKEN: String

    const val CLIENT_ID = 68429
    const val CLIENT_SECRET = "abd993c61a61331d54b8e09a676304373b46ad00"
    const val RESPONSE_CODE = ResponseTypeValues.CODE
    const val APPROVAL_PROMPT = "auto"

    const val AUTH_URL = "https://www.strava.com/api/v3/"
    const val CALLBACK_URL = "com.ybrflight552.fitnessapp://com.ybrflight552.fitnessapp"
    const val INTENT_URI = "https://www.strava.com/oauth/mobile/authorize"
    const val DEAUTHORIZE_URI = "https://www.strava.com"
    const val TOKEN_URL = "https://www.strava.com/oauth/token"
    const val SCOPE = "activity:write,read,profile:write,read_all,activity:read_all"
    const val TYPE = "refresh_token"

    const val WEB_URL_ATHLETE = "https://www.strava.com/athletes/"

    const val CONTACT_PERMISSIONS_CODE = 232

    const val KEY_EXIT = "exit"

    const val ONBOARDING_PREF_NAME = "onboarding_pref"
    const val ONBOARDING_PREF_KEY = "isSeen"

    const val LANGUAGE_RU = "ru"
    const val LANGUAGE_EN = "en"

    const val SHARED_TOKEN_NAME = "shared_token"
    const val SHARED_TOKEN_KEY = "shared_token_key"
    const val SHARED_ATHLETE_WEIGHT = "weight_float"
    const val SHARED_ACTIVITY_TIME = "activity_time"
    const val SHARED_EXPIRATION_TIME_KEY = "expiration_key"
    const val SHARED_LANGUAGE_KEY = "language_key"

    const val CREATE_NAME = "name"
    const val CREATE_TYPE = "type"
    const val CREATE_DATE = "date"
    const val CREATE_DESCRIPTION = "description"
    const val CREATE_DISTANCE = "distance"
    const val CREATE_DURATION = "duration"
    const val CREATE_FINISH = "finish"
    const val CREATE_UNKNOWN = "unknown"

    const val RESPONSE_SUCCESS = 101
    const val RESPONSE_ERROR_INTERNET = 202
    const val RESPONSE_ERROR_DATABASE = 303
    const val RESPONSE_ERROR = 404

}