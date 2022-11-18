package com.example.diary.model.businesses.entities

data class BusinessCreate(
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String,
)