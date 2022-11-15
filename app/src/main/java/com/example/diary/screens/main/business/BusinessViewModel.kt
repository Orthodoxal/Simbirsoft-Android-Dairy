package com.example.diary.screens.main.business

import com.example.diary.app.Singletons
import com.example.diary.model.businesses.entities.Business
import com.example.diary.model.businesses.entities.BusinessCreate
import com.example.diary.screens.base.BaseViewModel

class BusinessViewModel : BaseViewModel() {
    private val businessesRepository = Singletons.businessesRepository

    fun getAllBusinesses() = businessesRepository.getAllBusinesses()

    fun createBusiness(businessCreate: BusinessCreate) =
        businessesRepository.createBusiness(businessCreate)

    fun updateBusiness(business: Business) =
        businessesRepository.updateBusiness(business)

    fun deleteBusiness(id: Long) = businessesRepository.deleteBusiness(id)

}