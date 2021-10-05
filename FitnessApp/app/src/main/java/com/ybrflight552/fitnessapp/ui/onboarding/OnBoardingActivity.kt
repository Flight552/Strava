package com.ybrflight552.fitnessapp.ui.onboarding

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.ybrflight552.fitnessapp.MainActivity
import com.ybrflight552.fitnessapp.R
import com.ybrflight552.fitnessapp.databinding.OnboardingLayoutBinding
import com.ybrflight552.fitnessapp.utils.AuthInfo
import com.ybrflight552.fitnessapp.utils.locale.ChangeLanguage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: com.ybrflight552.fitnessapp.databinding.OnboardingLayoutBinding
    private lateinit var tvDots: Array<TextView?>
    private var displayLanguage = ""
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OnboardingLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getLanguage()
        binding.btSkip.setOnClickListener {
            openProfileFragment()
        }
        binding.btEng.setOnClickListener {
            displayLanguage = AuthInfo.LANGUAGE_EN
            changeSkipButton()
            addDots(0)
            setOnBoarding()
        }
        binding.btRu.setOnClickListener {
            displayLanguage = AuthInfo.LANGUAGE_RU
            changeSkipButton()
            addDots(0)
            setOnBoarding()
        }
        addDots(0)
        setOnBoarding()
    }

    private fun setOnBoarding() {
        saveLanguage(displayLanguage)
        val viewPager = findViewById<ViewPager>(R.id.viewPagerOnboarding)
        val adapter = OnboardingAdapter(this)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    addDots(position)
                }
                if (position == 2) {
                    lifecycleScope.launch {
                        saveOnboardingPref()
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

    }

    private fun saveOnboardingPref() {
        lifecycleScope.launch(Dispatchers.IO) {
            getSharedPreferences(AuthInfo.ONBOARDING_PREF_NAME, MODE_PRIVATE).apply {
                edit().putBoolean(AuthInfo.ONBOARDING_PREF_KEY, true)
                    .apply()
            }
        }
    }

    private fun getLanguage() {
        lifecycleScope.launch(Dispatchers.IO) {
            getSharedPreferences(AuthInfo.ONBOARDING_PREF_NAME, MODE_PRIVATE).apply {
                displayLanguage = getString(AuthInfo.SHARED_LANGUAGE_KEY, AuthInfo.LANGUAGE_EN)
                    ?: AuthInfo.LANGUAGE_EN
            }
        }
    }

    private fun saveLanguage(language: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            File.saveSharedLanguage(this@OnBoardingActivity, language)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun addDots(position: Int) {
        tvDots = arrayOfNulls<TextView?>(3)
        binding.onboardingLayoutImages.removeAllViews()
        for (i in tvDots.indices) {
            tvDots[i] = TextView(this)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvDots[i]?.text = Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY)
            }
            tvDots[i]?.textSize = 35F;
            tvDots[i]?.setTextColor(resources.getColor(R.color.purple_700, theme))
            binding.onboardingLayoutImages.addView(tvDots[i]);
        }

        if (tvDots.isNotEmpty()) {
            tvDots[position]?.setTextColor(resources.getColor(R.color.teal_200, theme));
        }
    }

    private fun openProfileFragment() {
        lifecycleScope.launch(Dispatchers.IO) {
            saveOnboardingPref()
        }
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun changeSkipButton() {
        ChangeLanguage.changeLanguage(displayLanguage, this)
        binding.btSkip.text = resources.getString(R.string.skip)
    }
}