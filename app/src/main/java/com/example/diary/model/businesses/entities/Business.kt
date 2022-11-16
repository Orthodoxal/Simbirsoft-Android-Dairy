package com.example.diary.model.businesses.entities

data class Business(
    val id: Long,
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String,
)
