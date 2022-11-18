package com.example.diary.model.businesses.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.diary.model.businesses.room.entities.BusinessDBEntity

@Dao
interface BusinessesDao {

    @Query("SELECT * FROM businesses")
    fun getAllBusinesses(): List<BusinessDBEntity>

    @Query("SELECT * FROM businesses WHERE ((date_start >= :start AND date_start < :end) OR (date_finish >= :start AND date_finish < :end)) ORDER BY date_start")
    fun filterBusinessesByTime(start: Long, end: Long): List<BusinessDBEntity>

    @Update(entity = BusinessDBEntity::class)
    fun updateBusiness(businessDBEntity: BusinessDBEntity)

    @Insert
    fun createBusiness(businessDBEntity: BusinessDBEntity)

    @Query("DELETE FROM businesses WHERE id = :id")
    fun deleteBusiness(id: Long)

    @Query("DELETE FROM businesses")
    fun deleteAll()

}