package com.example.diary.screens.main.business

import com.example.diary.app.IncorrectDateOrTime
import com.example.diary.app.Singletons
import com.example.diary.model.businesses.entities.Business
import com.example.diary.model.businesses.entities.BusinessCreate
import com.example.diary.model.date_time.IDateTimeFormatter
import com.example.diary.screens.base.BaseViewModel

class BusinessViewModel : BaseViewModel(), IDateTimeFormatter by Singletons.dateTimeFormatter {
    private val businessesRepository = Singletons.businessesRepository

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

    companion object {
        const val HOUR: Long = 3600 * 1000
    }

}