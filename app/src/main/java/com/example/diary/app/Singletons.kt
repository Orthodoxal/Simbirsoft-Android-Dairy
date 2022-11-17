package com.example.diary.app

import android.content.Context
import android.widget.Toast
import androidx.room.Room
import com.example.diary.model.businesses.IBusinessesRepository
import com.example.diary.model.businesses.room.RoomBusinessesRepository
import com.example.diary.model.room.AppDatabase

object Singletons {

    private lateinit var applicationContext: Context

    // -- stuffs

    private val database: AppDatabase by lazy<AppDatabase> {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .allowMainThreadQueries()
            .build()
    }

    // --- repositories

    val businessesRepository: IBusinessesRepository by lazy {
        RoomBusinessesRepository(database.getBusinessesDao())
    }

    // --- uiActions

    fun getString(id: Int) = applicationContext.resources.getString(id)

    fun init(context: Context) {
        applicationContext = context
    }

}