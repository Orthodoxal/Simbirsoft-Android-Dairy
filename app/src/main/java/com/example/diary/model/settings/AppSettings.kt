package com.example.diary.model.settings

import com.example.diary.screens.main.diary_todo_list.ViewMode

/**
 * Settings interface
 * @see [ViewMode]
 */
interface AppSettings {

    /**
     * Get last view mode
     * @return [ViewMode]
     */
    fun getViewMode(): ViewMode

    /**
     * @param [viewMode]
     * Set current view mode
     */
    fun setViewMode(viewMode: ViewMode)
}