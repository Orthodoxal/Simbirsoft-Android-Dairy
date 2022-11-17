package com.example.diary.screens.main.business

import com.example.diary.app.IncorrectDateOrTime
import com.example.diary.app.Singletons
import com.example.diary.model.businesses.entities.Business
import com.example.diary.model.businesses.entities.BusinessCreate
import com.example.diary.screens.base.BaseViewModel
import java.text.SimpleDateFormat
import java.util.*

class BusinessViewModel : BaseViewModel() {
    private val businessesRepository = Singletons.businessesRepository

    private val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        .apply { timeZone = TimeZone.getTimeZone("GMT") }
    private val simpleTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        .apply { timeZone = TimeZone.getTimeZone("GMT") }

    fun getAllBusinesses() = businessesRepository.getAllBusinesses()

    fun createBusiness(businessCreate: BusinessCreate) {
        if (businessCreate.dateStart >= businessCreate.dateFinish) {
            throw IncorrectDateOrTime()
        } else {
            businessesRepository.createBusiness(businessCreate)
        }
    }

    fun updateBusiness(business: Business) {
        if (business.dateStart >= business.dateFinish) {
            throw IncorrectDateOrTime()
        } else {
            businessesRepository.updateBusiness(business)
        }
    }

    fun deleteBusiness(id: Long) = businessesRepository.deleteBusiness(id)

    /**
     * @return Pair<Start<Date, Time>, Finish<Date, Time>>
     */
    fun getDefaultTimes(): Pair<Pair<String, String>, Pair<String, String>> {
        // get Default times
        simpleDateFormat.timeZone = TimeZone.getDefault()
        simpleTimeFormat.timeZone = TimeZone.getDefault()
        val currentDateTime = System.currentTimeMillis()
        val currentDateTimeNextHour = currentDateTime + HOUR
        val startDate = simpleDateFormat.format(currentDateTime)
        val startTime = simpleTimeFormat.format(currentDateTime)
        val finishDate = simpleDateFormat.format(currentDateTimeNextHour)
        val finishTime = simpleTimeFormat.format(currentDateTimeNextHour)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        simpleTimeFormat.timeZone = TimeZone.getTimeZone("GMT")
        return Pair(Pair(startDate, startTime), Pair(finishDate, finishTime))
    }

    fun getDate(millis: Long, default: Boolean = false): String {
        if (default) {
            simpleDateFormat.timeZone = TimeZone.getDefault()
            simpleTimeFormat.timeZone = TimeZone.getDefault()
        }
        val result = simpleDateFormat.format(millis)
        if (default) {
            simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
            simpleTimeFormat.timeZone = TimeZone.getTimeZone("GMT")
        }
        return result
    }

    fun getTime(millis: Long, default: Boolean = false): String {
        if (default) {
            simpleDateFormat.timeZone = TimeZone.getDefault()
            simpleTimeFormat.timeZone = TimeZone.getDefault()
        }
        val result = simpleTimeFormat.format(millis)
        if (default) {
            simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
            simpleTimeFormat.timeZone = TimeZone.getTimeZone("GMT")
        }
        return result
    }

    fun getTimes(date: String, time: String): Long {
        val dateParse = simpleDateFormat.parse(date)
        val timeParse = simpleTimeFormat.parse(time)
        return if (dateParse != null && timeParse != null) dateParse.time + timeParse.time
        else throw Exception()
    }

    companion object {
        const val HOUR: Long = 3600 * 1000
    }

}