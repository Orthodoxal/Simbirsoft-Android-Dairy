package com.example.diary.model.businesses

import com.example.diary.model.businesses.entities.Business
import com.example.diary.model.businesses.entities.BusinessCreate

interface IBusinessesRepository {

    fun getAllBusinesses(): List<Business>

    fun createBusiness(businessCreate: BusinessCreate)

    fun updateBusiness(business: Business)

    fun deleteBusiness(id: Long)

}