package com.example.diary.screens.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Base ViewModel class for custom methods based on [ViewModel]
 */
open class BaseViewModel : ViewModel() {
    fun <T> MutableLiveData<T>.share(): LiveData<T> = this
}