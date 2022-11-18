package com.example.diary.app

import com.example.diary.R

class IncorrectDateOrTimeException : Exception(Singletons.getString(R.string.incorrect_time_exception))

class RequiredFieldIsEmptyException(fieldName: String)
    : Exception(Singletons.getString(R.string.empty_field_exception, fieldName))