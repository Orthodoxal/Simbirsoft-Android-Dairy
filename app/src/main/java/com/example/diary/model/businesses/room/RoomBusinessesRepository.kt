package com.example.diary.model.businesses.room

import com.example.diary.model.businesses.IBusinessesRepository
import com.example.diary.model.businesses.entities.Business
import com.example.diary.model.businesses.entities.BusinessCreate
import com.example.diary.model.businesses.room.entities.BusinessDBEntity

class RoomBusinessesRepository(
    private val businessesDao: BusinessesDao
) : IBusinessesRepository {

    override fun getAllBusinesses(): List<Business> =
        businessesDao.getAllBusinesses().map { it.toBusiness() }

    override fun createBusiness(businessCreate: BusinessCreate) =
        businessesDao.createBusiness(BusinessDBEntity.fromBusinessCreate(businessCreate))

    override fun updateBusiness(business: Business) =
        businessesDao.updateBusiness(BusinessDBEntity.fromBusiness(business))

    override fun deleteBusiness(id: Long) = businessesDao.deleteBusiness(id)

    override fun deleteAll() = businessesDao.deleteAll()

}