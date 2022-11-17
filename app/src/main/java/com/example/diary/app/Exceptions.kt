package com.example.diary.app

import com.example.diary.R

class IncorrectDateOrTime : Exception(Singletons.getString(R.string.incorrect_time_toast))