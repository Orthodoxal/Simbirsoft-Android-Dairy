package com.example.diary.model.businesses

import com.example.diary.model.businesses.entities.Business
import com.example.diary.model.businesses.entities.BusinessCreate
import kotlinx.coroutines.flow.Flow

interface IBusinessesRepository {

    /**
     * Get all available businesses
     * @return [Flow]
     */
    fun getAllBusinesses(): Flow<List<Business>>

    /**
     * Get filtered businesses from start to end
     * @return [Flow]
     */
    fun filterBusinessesByTime(start: Long, end: Long): Flow<List<Business>>

    /**
     * Create business
     * @param [businessCreate]
     */
    suspend fun createBusiness(businessCreate: BusinessCreate)

    /**
     * Update business
     * @param [business]
     */
    suspend fun updateBusiness(business: Business)

    /**
     * Delete business by Id
     * @param [id]
     */
    suspend fun deleteBusiness(id: Long)

    /**
     * Delete all businesses
     */
    suspend fun deleteAll()

}