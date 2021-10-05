package com.ybrflight552.fitnessapp.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ybrflight552.fitnessapp.utils.AuthInfo
import com.ybrflight552.fitnessapp.R
import com.ybrflight552.fitnessapp.databinding.FragmentAuthBinding
import com.ybrflight552.fitnessapp.utils.locale.ChangeLanguage
import com.ybrflight552.fitnessapp.ui.onboarding.File
import com.ybrflight552.fitnessapp.utils.lib.ViewBindingFragment
import com.ybrflight552.fitnessapp.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

@AndroidEntryPoint
class AuthFragment : ViewBindingFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    private val viewModel: AuthViewModel by viewModels()
    private val args: AuthFragmentArgs by navArgs()

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val getIntent = it.data
            if (getIntent != null) {
                val tokenExchangeRequest = AuthorizationResponse.fromIntent(getIntent)
                    ?.createTokenExchangeRequest()
                val exception = AuthorizationException.fromIntent(getIntent)
                when {
                    tokenExchangeRequest != null && exception == null -> {
                        binding.barProgress.isVisible = true
                        viewModel.onAuthCodeReceived(tokenExchangeRequest)
                    }
                    exception != null -> viewModel.onAuthCodeFailed(exception)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exitApp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        getTokenSharedPref()
        binding.btEng.setOnClickListener {
            chooseLanguage(AuthInfo.LANGUAGE_EN)
        }
        binding.btRus.setOnClickListener {
            chooseLanguage(AuthInfo.LANGUAGE_RU)
        }
    }

    private fun bindViewModel() {
        binding.typeOutlin.setOnClickListener {
            viewModel.openLoginPage()
        }
        viewModel.openAuthPageLiveData.observe(viewLifecycleOwner, ::openAuthPage)
        viewModel.authSuccessLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToProfileFragment())
            binding.barProgress.isVisible = false
        }
        viewModel.toast.observe(viewLifecycleOwner) { text ->
            Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
        }
    }

    private fun openAuthPage(intent: Intent) {
        getContent.launch(intent)
    }

    // проверяем время окончания действия токена, если времени достаточно - переходим на фрагмент профиля
    // внутри профиля есть еще одна проверка на время при запросе активностей
    private fun getTokenSharedPref() {
        lifecycleScope.launch {
            viewModel.getSharedPrefs()
            viewModel.isShared.observe(viewLifecycleOwner) { isShared ->
                if (isShared && args.exitKey != AuthInfo.KEY_EXIT) {
                    findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToProfileFragment())
                }
            }
        }
    }

    private fun chooseLanguage(language: String) {
        ChangeLanguage.changeLanguage(language, requireContext())
        when (language) {
            "en" -> {
                binding.textLabel.text = resources.getString(R.string.text_label)
                binding.tvContinueWi.text = resources.getString(R.string.continue_wi)
            }
            "ru" -> {
                binding.textLabel.text = resources.getString(R.string.text_label)
                binding.tvContinueWi.text = resources.getString(R.string.continue_wi)
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            File.saveSharedLanguage(requireContext(), language)
        }
    }

    private fun exitApp() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
}