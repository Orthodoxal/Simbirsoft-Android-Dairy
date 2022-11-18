package com.example.diary.model.date_time

import com.example.diary.screens.main.business.BusinessViewModel
import java.text.SimpleDateFormat
import java.util.*

class DateTimeFormatter : IDateTimeFormatter {
    private val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        .apply { timeZone = TimeZone.getTimeZone("GMT") }
    private val simpleTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        .apply { timeZone = TimeZone.getTimeZone("GMT") }

    /**
     * @return Pair<Start<Date, Time>, Finish<Date, Time>>
     */
    override fun getDefaultTimes(): Pair<Pair<String, String>, Pair<String, String>> {
        // get Default times
        simpleDateFormat.timeZone = TimeZone.getDefault()
        simpleTimeFormat.timeZone = TimeZone.getDefault()
        val currentDateTime = System.currentTimeMillis()
        val currentDateTimeNextHour = currentDateTime + BusinessViewModel.HOUR
        val startDate = simpleDateFormat.format(currentDateTime)
        val startTime = simpleTimeFormat.format(currentDateTime)
        val finishDate = simpleDateFormat.format(currentDateTimeNextHour)
        val finishTime = simpleTimeFormat.format(currentDateTimeNextHour)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        simpleTimeFormat.timeZone = TimeZone.getTimeZone("GMT")
        return Pair(Pair(startDate, startTime), Pair(finishDate, finishTime))
    }

    override fun getDate(millis: Long, default: Boolean): String {
        if (default) {
            simpleDateFormat.timeZone = TimeZone.getDefault()
        }
        val result = simpleDateFormat.format(millis)
        if (default) {
            simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        }
        return result
    }

    override fun getTime(millis: Long, default: Boolean): String {
        if (default) {
            simpleTimeFormat.timeZone = TimeZone.getDefault()
        }
        val result = simpleTimeFormat.format(millis)
        if (default) {
            simpleTimeFormat.timeZone = TimeZone.getTimeZone("GMT")
        }
        return result
    }

    override fun getTimes(date: String, time: String): Long {
        val dateParse = simpleDateFormat.parse(date)
        val timeParse = simpleTimeFormat.parse(time)
        return if (dateParse != null && timeParse != null) dateParse.time + timeParse.time
        else throw Exception()
    }
}