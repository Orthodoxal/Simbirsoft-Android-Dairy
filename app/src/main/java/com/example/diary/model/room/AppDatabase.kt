package com.example.diary.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.diary.model.businesses.room.BusinessesDao
import com.example.diary.model.businesses.room.entities.BusinessDBEntity

@Database(
    version = 1,
    entities = [
        BusinessDBEntity::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getBusinessesDao(): BusinessesDao

}