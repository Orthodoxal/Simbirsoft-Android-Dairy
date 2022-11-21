package com.example.diary.model.businesses.room

import com.example.diary.model.businesses.IBusinessesRepository
import com.example.diary.model.businesses.entities.Business
import com.example.diary.model.businesses.entities.BusinessCreate
import com.example.diary.model.businesses.room.entities.BusinessDBEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomBusinessesRepository(
    private val businessesDao: BusinessesDao
) : IBusinessesRepository {

    override fun getAllBusinesses(): Flow<List<Business>> =
        businessesDao.getAllBusinesses().map { businesses -> businesses.map { it.toBusiness() } }

    override suspend fun createBusiness(businessCreate: BusinessCreate) =
        businessesDao.createBusiness(BusinessDBEntity.fromBusinessCreate(businessCreate))

    override suspend fun updateBusiness(business: Business) =
        businessesDao.updateBusiness(BusinessDBEntity.fromBusiness(business))

    override suspend fun deleteBusiness(id: Long) = businessesDao.deleteBusiness(id)

    override suspend fun deleteAll() = businessesDao.deleteAll()

    override suspend fun filterBusinessesByTime(start: Long, end: Long): Flow<List<Business>> =
        businessesDao.filterBusinessesByTime(start, end).map { businesses -> businesses.map { it.toBusiness() } }

}