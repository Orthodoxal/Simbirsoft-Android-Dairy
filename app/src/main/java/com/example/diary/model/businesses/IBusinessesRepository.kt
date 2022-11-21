package com.example.diary.model.businesses

import com.example.diary.model.businesses.entities.Business
import com.example.diary.model.businesses.entities.BusinessCreate
import kotlinx.coroutines.flow.Flow

interface IBusinessesRepository {

    fun getAllBusinesses(): Flow<List<Business>>

    suspend fun filterBusinessesByTime(start: Long, end: Long): Flow<List<Business>>

    suspend fun createBusiness(businessCreate: BusinessCreate)

    suspend fun updateBusiness(business: Business)

    suspend fun deleteBusiness(id: Long)

    suspend fun deleteAll()

}