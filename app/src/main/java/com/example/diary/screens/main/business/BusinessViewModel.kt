package com.example.diary.screens.main.business

import androidx.lifecycle.viewModelScope
import com.example.diary.app.IncorrectDateOrTimeException
import com.example.diary.app.Singletons
import com.example.diary.model.businesses.entities.Business
import com.example.diary.model.businesses.entities.BusinessCreate
import com.example.diary.model.date_time.IDateTimeFormatter
import com.example.diary.screens.base.BaseViewModel
import kotlinx.coroutines.launch

class BusinessViewModel : BaseViewModel(), IDateTimeFormatter by Singletons.dateTimeFormatter {
    private val businessesRepository = Singletons.businessesRepository

    // fun getAllBusinesses() = businessesRepository.getAllBusinesses()

    fun createBusiness(businessCreate: BusinessCreate) {
        if (businessCreate.dateStart >= businessCreate.dateFinish) {
            throw IncorrectDateOrTimeException()
        } else {
            viewModelScope.launch {
                businessesRepository.createBusiness(businessCreate)
            }
        }
    }

    fun updateBusiness(business: Business) {
        if (business.dateStart >= business.dateFinish) {
            throw IncorrectDateOrTimeException()
        } else {
            viewModelScope.launch {
                businessesRepository.updateBusiness(business)
            }
        }
    }

    fun deleteBusiness(id: Long) = viewModelScope.launch {
        businessesRepository.deleteBusiness(id)
    }

    companion object {
        const val HOUR: Long = 3600000
    }

}