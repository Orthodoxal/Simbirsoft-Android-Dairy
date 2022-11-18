package com.example.diary.app

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Singletons.init(context = baseContext)
    }
}