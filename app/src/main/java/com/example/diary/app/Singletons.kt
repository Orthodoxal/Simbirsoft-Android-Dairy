package com.example.diary.app

import android.content.Context
import android.widget.Toast
import androidx.room.Room
import com.example.diary.model.businesses.IBusinessesRepository
import com.example.diary.model.businesses.room.RoomBusinessesRepository
import com.example.diary.model.date_time.DateTimeFormatter
import com.example.diary.model.date_time.IDateTimeFormatter
import com.example.diary.model.room.AppDatabase
import com.example.diary.model.settings.AppSettings
import com.example.diary.model.settings.SharedPreferencesAppSettings

object Singletons {

    private lateinit var applicationContext: Context

    // -- stuffs

    val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(applicationContext)
    }

    private val database: AppDatabase by lazy<AppDatabase> {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .build()
    }

    // --- repositories

    val businessesRepository: IBusinessesRepository by lazy {
        RoomBusinessesRepository(database.getBusinessesDao())
    }

    val dateTimeFormatter: IDateTimeFormatter by lazy {
        DateTimeFormatter()
    }

    // --- uiActions

    fun getString(id: Int) = applicationContext.resources.getString(id)

    fun getString(id: Int, vararg formatArgs: Any) =
        applicationContext.resources.getString(id, *formatArgs)

    fun init(context: Context) {
        applicationContext = context
    }

}