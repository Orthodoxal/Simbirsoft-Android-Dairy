package com.example.diary.model.date_time

interface IDateTimeFormatter {

    fun getDefaultTimes(startTimes: Long): Pair<Pair<String, String>, Pair<String, String>>

    fun getDate(millis: Long, default: Boolean = false): String

    fun getTime(millis: Long, default: Boolean = false): String

    fun getTimes(date: String, time: String): Long

    fun getHour(hour: String): Long

}