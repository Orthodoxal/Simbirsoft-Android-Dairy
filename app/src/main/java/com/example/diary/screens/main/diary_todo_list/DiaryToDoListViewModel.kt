package com.example.diary.screens.main.diary_todo_list

import com.example.diary.app.Singletons
import com.example.diary.model.date_time.IDateTimeFormatter
import com.example.diary.screens.base.BaseViewModel

class DiaryToDoListViewModel : BaseViewModel(), IDateTimeFormatter by Singletons.dateTimeFormatter {
    private val businessesRepository = Singletons.businessesRepository

    fun getAllBusinesses() = businessesRepository.getAllBusinesses()

    fun getFilteredBusinessesByDay(millisStart: Long) =
        businessesRepository.filterBusinessesByTime(millisStart, millisStart + DAY)

    fun deleteAllBusinesses() = businessesRepository.deleteAll()

    companion object {
        const val DAY: Long = 86400000
    }
}