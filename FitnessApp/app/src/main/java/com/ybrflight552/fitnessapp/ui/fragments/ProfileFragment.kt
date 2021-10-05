package com.ybrflight552.fitnessapp.ui.fragments

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ybrflight552.fitnessapp.utils.AppPermissions
import com.ybrflight552.fitnessapp.MainActivity
import com.ybrflight552.fitnessapp.R
import com.ybrflight552.fitnessapp.database.entities.AthleteEntity
import com.ybrflight552.fitnessapp.databinding.FragmentProfileBinding
import com.ybrflight552.fitnessapp.utils.AuthInfo
import com.ybrflight552.fitnessapp.utils.lib.ViewBindingFragment
import com.ybrflight552.fitnessapp.viewModel.AthleteRequestsViewModel
import java.util.*
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.ybrflight552.fitnessapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment :
    ViewBindingFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private var athleteID = 0L
    private var athleteWeight: Float? = null
    private val viewModel: AthleteRequestsViewModel by viewModels()

    private val contactContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sendFlagToAuthFragment()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLanguage()
        getAthleteProfile()
        saveWeightToServer()
        binding.btLogout.setOnClickListener {
            showConfirmationDialog()
        }

        binding.ivSheet.setOnClickListener {
            getAthleteActivity()
        }

        binding.btShare.setOnClickListener {
            shareProfile()
        }


    }

    //Запись веса атлета
    private fun saveWeightToServer() {
        var timer: Timer? = null
        val delay = 2000L
        binding.etWeight.doOnTextChanged { text, _, _, _ ->
            timer?.cancel()
            if (!text.isNullOrEmpty()) {
                timer = Timer()
                if (text.length >= 2) {
                    timer?.schedule(object : TimerTask() {
                        override fun run() {
                            // проверяем изменения веса атлета, если вес изменился то отправляем
                            if (athleteWeight != text.toString().toFloat()) {
                                lifecycleScope.launch {
                                    viewModel.submitFLowWeight(text)
                                    viewModel.onSuccess.observe(viewLifecycleOwner) {
                                        binding.etWeight.isEnabled = it
                                        binding.barProgress.isVisible = !it
                                    }
                                    viewModel.onError.observe(viewLifecycleOwner) { error ->
                                        toastError(error)
                                    }
                                }
                            }
                        }

                    }, delay)
                }
            }
        }
    }

    private fun toastError(error: Int) {
        when (error) {
            AuthInfo.RESPONSE_SUCCESS -> {
                getString(R.string.add_weight_message).showToast(
                    requireContext()
                )
            }
            AuthInfo.RESPONSE_ERROR_INTERNET -> {
                getString(R.string.add_weight_message_error_internet).showToast(
                    requireContext()
                )
            }
            AuthInfo.RESPONSE_ERROR_DATABASE -> {
                getString(R.string.add_weight_message_error_db).showToast(requireContext())
            }
            else -> {
                getString(R.string.add_weight_message_error).showToast(requireContext())
            }
        }
    }

    //получение детальной информации об атлете и установка во фрагмент
    private fun getAthleteProfile() {
        viewModel.athleteProfile()
        binding.barProgress.isVisible = true
        viewModel.remoteAthlete.observe(viewLifecycleOwner) { athlete ->
            if (athlete != null) {
                athleteID = athlete.id
                setAthlete(athlete)
            } else {
                getString(R.string.query_limit_message).showToast(requireContext())
            }
            binding.barProgress.isVisible = false
        }
    }

    private fun getAthleteActivity() {
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToListActivities())
    }

    private fun setAthlete(athlete: AthleteEntity) {
        val fullName = "${athlete.firstname} ${athlete.lastname}"
        val followers = athlete.follower_count ?: 0
        val following = athlete.friend_count ?: 0
        athleteWeight = athlete.weight
        binding.tvUserCountry.text = athlete.country
        binding.tvUserFollowers.text = followers.toString()
        binding.tvUserName.text = fullName
        binding.tvUserGender.text = athlete.sex
        binding.tvUserFollowing.text = following.toString()
        binding.etWeight.setText(athlete.weight.toString())
        getProfileImage(athlete.profile)
    }

    //деавторизация приложения
    private fun deauthorization() {
        viewModel.deauthorizeRequest()
        viewModel.onSuccess.observe(viewLifecycleOwner) {
            val oldIntent = Intent(requireContext(), MainActivity::class.java)
            oldIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(oldIntent)
        }
    }

    // удалить все на выходе
    private fun deleteDBonLogout() {
        viewModel.deleteAll()
    }

    //Загрузка аватара атлета во фрагмент
    private fun getProfileImage(url: String?) {
        Glide
            .with(this)
            .load(url)
            .into(binding.ivAvatar)
    }

    //Окно диалога о выходе из приложения
    private fun showConfirmationDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.logout_dialog_title))
            .setMessage(resources.getString(R.string.logout_dialog_message))
            .setNegativeButton(
                resources.getString(R.string.logout_dialog_no)
            ) { _, _ ->

            }
            .setPositiveButton(
                resources.getString(R.string.logout_dialog_yes)
            ) { _, _ ->
                deleteDBonLogout()
                deauthorization()
            }
            .create()
        dialog.show()
    }

    // поделиться профилем с контактами из записной книги
    private fun shareProfile() {
        if (AppPermissions.checkContactPermissions(requireContext())) {
            sendSms()
        } else {
            AppPermissions.requestContactPermissions(requireActivity())
        }
    }

    // открыть интент смс
    private fun sendSms() {
        val message = "${getString(R.string.send_sms)}: ${AuthInfo.WEB_URL_ATHLETE}$athleteID"
        val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:")
            putExtra("sms_body", message)
        }
        contactContent.launch(smsIntent)
    }

    private fun setLanguage() {
        viewModel.getLanguage()
        viewModel.onResponse.observe(viewLifecycleOwner) { lang ->
            setLocale(lang)
            bindLanguage()
        }
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    private fun bindLanguage() {
        binding.tvCountry.text = resources.getString(R.string.country)
        binding.btShare.text = resources.getString(R.string.share)
        binding.btLogout.text = resources.getString(R.string.logout)
        binding.tvFollowers.text = resources.getString(R.string.followers)
        binding.tvFollowing.text = resources.getString(R.string.following)
        binding.tvProfile.text = resources.getText(R.string.profile)
        binding.tvWeight.text = resources.getString(R.string.weight)
        binding.tvConnectionStatus.text = resources.getString(R.string.no_connection)
    }

    private fun sendFlagToAuthFragment() {
        findNavController().navigate(
            ProfileFragmentDirections.actionProfileFragmentToAuthFragment(AuthInfo.KEY_EXIT)
        )
    }


}