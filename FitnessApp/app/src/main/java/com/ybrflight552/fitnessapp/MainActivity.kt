package com.ybrflight552.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.ybrflight552.fitnessapp.ui.onboarding.File
import com.ybrflight552.fitnessapp.ui.onboarding.OnBoardingActivity
import com.ybrflight552.fitnessapp.databinding.ActivityMainBinding
import com.ybrflight552.fitnessapp.internet.network.internet.ConnectionLiveData
import com.ybrflight552.fitnessapp.utils.AuthInfo
import com.ybrflight552.fitnessapp.viewModel.AthleteRequestsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var connectionLiveData: ConnectionLiveData
    private val viewModel: AthleteRequestsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getOnboardingSharedPref()
        checkForInternetConnection()
    }

    // проверяем если пользователь уже простмотрел заставку
    // если смотрел переходим на фрагмент авторизации
    private fun getOnboardingSharedPref() {
        val isSeen = File.getOnBoardingInfo(this@MainActivity)
        if (!isSeen) {
            startActivity(Intent(this@MainActivity, OnBoardingActivity::class.java))
        }
    }

    // Проверяем наличие интернета
    private fun checkForInternetConnection() {
        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.toast.observe(this) { text ->
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }

        //проверяем если есть подключение к wi-fi или мобильная дата при переходе активностей
        connectionLiveData.isConnected.observe(this) { hasConnection ->
            binding.tvConnectionStatus.isVisible = !hasConnection
        }

        //проверяем если пропадает связь
        connectionLiveData.observe(this, { isNetworkAvailable ->
            Log.d("TokenEmpty", "Token ${AuthInfo.ACCESS_TOKEN}")
            if (isNetworkAvailable && AuthInfo.ACCESS_TOKEN != null) {
                Log.d("TokenEmpty", "Update DB")
                update()
            }
            binding.tvConnectionStatus.isVisible = !isNetworkAvailable

            // если связи нет - показать уведомление
        })
    }

    private fun update() {
        lifecycleScope.launch {
            try {
                viewModel.updateLocalDB()
            } catch (e: Exception) {
                Log.d("AthleteRequests" , "Add weight error ${e}")
            }
        }
    }
}