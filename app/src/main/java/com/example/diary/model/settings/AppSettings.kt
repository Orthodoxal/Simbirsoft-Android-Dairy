package com.example.diary.model.settings

import com.example.diary.screens.main.diary_todo_list.ViewMode

interface AppSettings {

    fun getViewMode(): ViewMode

    fun setViewMode(viewMode: ViewMode)
}