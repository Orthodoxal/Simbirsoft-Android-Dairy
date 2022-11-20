package com.example.diary.screens.main.diary_todo_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.diary.app.Singletons
import com.example.diary.model.businesses.entities.Business
import com.example.diary.model.date_time.IDateTimeFormatter
import com.example.diary.screens.base.BaseViewModel
import kotlinx.coroutines.launch

class DiaryToDoListViewModel : BaseViewModel(), IDateTimeFormatter by Singletons.dateTimeFormatter {
    private val businessesRepository = Singletons.businessesRepository

    private val _businessesByDate = MutableLiveData<List<Business>>()
    val businessesByDate = _businessesByDate.share()

    //fun getAllBusinesses() = businessesRepository.getAllBusinesses()

    fun getFilteredBusinessesByDay(millisStart: Long) = viewModelScope.launch {
        businessesRepository.filterBusinessesByTime(millisStart, millisStart + DAY).collect {
            _businessesByDate.value = it
        }
    }

    fun deleteAllBusinesses() = viewModelScope.launch { businessesRepository.deleteAll() }

    companion object {
        const val DAY: Long = 86400000
    }

}