package com.ybrflight552.fitnessapp.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ybrflight552.fitnessapp.R
import com.ybrflight552.fitnessapp.internet.data.ActivityType
import com.ybrflight552.fitnessapp.databinding.FragmentCreateActivityBinding
import com.ybrflight552.fitnessapp.utils.AuthInfo
import com.ybrflight552.fitnessapp.utils.lib.ViewBindingFragment
import com.ybrflight552.fitnessapp.utils.showToast
import com.ybrflight552.fitnessapp.viewModel.AthleteRequestsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class CreateActivityFragment :
    ViewBindingFragment<FragmentCreateActivityBinding>(FragmentCreateActivityBinding::inflate) {

    private val viewModel: AthleteRequestsViewModel by viewModels()

    private var dateSet: String? = null
    private var timeSet: Long? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etDate.setOnClickListener {
            pickDateTime()
        }

        binding.btAddActivity.setOnClickListener {
            createActivity()
        }
    }

    private fun createActivity() {
        val distance = binding.etDistance.text.toString()
        val name = binding.etName.text.toString()
        val type = spinnerConverter(binding.spinnerType.selectedItemPosition)
        val description = binding.etDescription.text.toString()
        val duration = binding.etDuration.text.toString()
        val date = dateSet
        val time = timeSet ?: 0L
        lifecycleScope.launch {
            viewModel.createAthleteActivity(name, type, date, time, description, distance, duration)
            viewModel.onSuccess.observe(viewLifecycleOwner) {
                binding.barProgress.isVisible = it
            }
            viewModel.onResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    AuthInfo.CREATE_NAME -> "${getString(R.string.field_warning)} - ${getString(R.string.enter_title)}".showToast(
                        requireContext()
                    )
                    AuthInfo.CREATE_TYPE -> "${getString(R.string.field_warning)} -  ${getString(R.string.type)}".showToast(
                        requireContext()
                    )
                    AuthInfo.CREATE_DATE -> "${getString(R.string.field_warning)} -  ${getString(R.string.enter_date)}".showToast(
                        requireContext()
                    )
                    AuthInfo.CREATE_DESCRIPTION -> "${getString(R.string.field_warning)} -  ${
                        getString(
                            R.string.enter_description
                        )
                    }".showToast(
                        requireContext()
                    )
                    AuthInfo.CREATE_DISTANCE -> "${getString(R.string.field_warning)} -  ${
                        getString(
                            R.string.enter_distance
                        )
                    }".showToast(
                        requireContext()
                    )
                    AuthInfo.CREATE_DURATION -> "${getString(R.string.field_warning)} -  ${
                        getString(
                            R.string.enter_duration
                        )
                    }".showToast(
                        requireContext()
                    )
                    AuthInfo.CREATE_FINISH -> {
                        findNavController().popBackStack()
                    }
                }
            }
        }

    }

    private fun spinnerConverter(position: Int): String {
        return when (position) {
            0 -> resources.getString(R.string.choose_activity)
            1 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.AlpineSki.name
            2 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.BackcountrySki.name
            3 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Canoeing.name
            4 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Crossfit.name
            5 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.EBikeRide.name
            6 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Elliptical.name
            7 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Golf.name
            8 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Handcycle.name
            9 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Hike.name
            10 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.IceSkate.name
            11 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.InlineSkate.name
            12 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Kayaking.name
            13 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Kitesurf.name
            14 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.NordicSki.name
            15 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Ride.name
            16 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.RockClimbing.name
            17 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.RollerSki.name
            18 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Rowing.name
            19 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Run.name
            20 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Sail.name
            21 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Skateboard.name
            22 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Snowboard.name
            23 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Snowshoe.name
            24 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Soccer.name
            25 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.StairStepper.name
            26 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.StandUpPaddling.name
            27 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Surfing.name
            28 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Swim.name
            29 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Velomobile.name
            30 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.VirtualRide.name
            31 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.VirtualRun.name
            32 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Walk.name
            33 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.WeightTraining.name
            34 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Wheelchair.name
            35 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Windsurf.name
            36 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Workout.name
            37 -> com.ybrflight552.fitnessapp.internet.data.ActivityType.Yoga.name
            else -> AuthInfo.CREATE_UNKNOWN
        }
    }

    private fun pickDateTime() {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                TimePickerDialog(
                    requireContext(),
                    TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                        val myLocale = Locale("ru", "RU")
                        val format = "yyyy-MM-dd'T'HH:mm:ss'Z'"
                        val userFormat = "dd MMMM yyyy HH:mm"
                        val pickedDateTime = Calendar.getInstance()
                        pickedDateTime.set(year, month, day, hour, minute)
                        val sdf = SimpleDateFormat(format, myLocale)
                        val date = sdf.format(pickedDateTime.time)
                        // set display date
                        val userSDF = SimpleDateFormat(userFormat, myLocale)
                        val friendlyDate = userSDF.format(pickedDateTime.time)
                        dateSet = date
                        timeSet = pickedDateTime.timeInMillis
                        setDateTimeText(friendlyDate)
                    },
                    startHour,
                    startMinute,
                    true
                ).show()
            },
            startYear,
            startMonth,
            startDay
        ).show()
    }

    private fun setDateTimeText(date: String) {
        binding.etDate.setText(date)
    }
}