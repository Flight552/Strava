package com.ybrflight552.fitnessapp.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun EditText.transformIntoDatePicker(
    context: Context,
    maxDate: Date? = null,
    getDate : (String) -> Unit
){
    val myCalendar = Calendar.getInstance()
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val myLocale = Locale("ru", "RU")
            val format = "yyyy-MM-dd"
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat(format, myLocale)
            val date = sdf.format(myCalendar.time)
            getDate(date)
            setText(date)
        }

    setOnClickListener {
        DatePickerDialog(
            context, datePickerOnDataSetListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).run {
            maxDate?.time?.also { datePicker.maxDate = it }
            show()
        }
    }
}

fun EditText.transformIntoTimePicker(context: Context, getTime : (String) -> Unit) {
    val myCalendar = Calendar.getInstance()
    val timePickerOnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val chosenTime = "$hourOfDay:$minute"
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            myCalendar.set(Calendar.MINUTE, minute)
            getTime(chosenTime)
            setText(chosenTime)
        }

    setOnClickListener {
        TimePickerDialog(
            context,
            timePickerOnTimeSetListener,
            Calendar.HOUR_OF_DAY,
            Calendar.MINUTE,
            true
        )
            .run {
                show()
            }
    }
}

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}