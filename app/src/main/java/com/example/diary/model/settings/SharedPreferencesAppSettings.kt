package com.example.diary.model.settings

import android.content.Context
import android.content.SharedPreferences
import com.example.diary.screens.main.diary_todo_list.ViewMode

/**
 * Implementation of [AppSettings] based on [SharedPreferences].
 */
class SharedPreferencesAppSettings(
    appContext: Context
) : AppSettings {

    private val sharedPreferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)


    override fun getViewMode(): ViewMode {
        val name = sharedPreferences.getString(VIEW_MODE, null)
        return name?.let { ViewMode.valueOf(it) } ?: ViewMode.LIST
    }

    override fun setViewMode(viewMode: ViewMode) {
        val editor = sharedPreferences.edit()
        editor.putString(VIEW_MODE, viewMode.name)
        editor.apply()
    }

    companion object {
        /**
         * Key for view mode in [SharedPreferences]
         */
        private const val VIEW_MODE = "VIEW_MODE"
    }
}