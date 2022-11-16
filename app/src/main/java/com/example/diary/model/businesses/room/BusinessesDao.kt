package com.example.diary.model.businesses.room

import androidx.room.*
import com.example.diary.model.businesses.room.entities.BusinessDBEntity

@Dao
interface BusinessesDao {

    @Query("SELECT * FROM businesses")
    fun getAllBusinesses(): List<BusinessDBEntity>

    @Update(entity = BusinessDBEntity::class)
    fun updateBusiness(businessDBEntity: BusinessDBEntity)

    @Insert
    fun createBusiness(businessDBEntity: BusinessDBEntity)

    @Query("DELETE FROM businesses WHERE id = :id")
    fun deleteBusiness(id: Long)

    @Query("DELETE FROM businesses")
    fun deleteAll()

}