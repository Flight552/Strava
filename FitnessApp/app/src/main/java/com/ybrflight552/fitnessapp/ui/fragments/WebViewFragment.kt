package com.ybrflight552.fitnessapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView

import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.ybrflight552.fitnessapp.R
import com.ybrflight552.fitnessapp.utils.AuthInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment: AppCompatActivity(R.layout.web_view) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setWebView()
    }


    // получаем id из линка
    private fun getId(): String? {
        return intent?.data?.lastPathSegment
    }


    // устанавливаем webView
    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        val myWebView = findViewById<WebView>(R.id.webView)
        myWebView.webViewClient = WebViewClient()
        val segment = getId()
        if(segment != null) {
            val myUrl = "${AuthInfo.WEB_URL_ATHLETE}$segment"
            myWebView.loadUrl(myUrl)
        } else {
            myWebView.loadUrl(AuthInfo.WEB_URL_ATHLETE)
        }
        val webSettings = myWebView.settings
        webSettings.allowContentAccess = true
        webSettings.javaScriptEnabled = true
        webSettings.loadsImagesAutomatically = true
    }


}