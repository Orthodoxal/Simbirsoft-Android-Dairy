package com.example.diary.screens.main.diary_todo_list

import com.example.diary.app.Singletons
import com.example.diary.screens.base.BaseViewModel

class DiaryToDoListViewModel : BaseViewModel() {
    private val businessesRepository = Singletons.businessesRepository

    fun getAllBusinesses() = businessesRepository.getAllBusinesses()

    fun deleteAllBusinesses() = businessesRepository.deleteAll()
}