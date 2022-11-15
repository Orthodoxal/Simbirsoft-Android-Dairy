package com.example.diary.model.businesses.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diary.model.businesses.entities.Business
import com.example.diary.model.businesses.entities.BusinessCreate

@Entity(
    tableName = "businesses"
)
data class BusinessDBEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "date_start") val dateStart: Long,
    @ColumnInfo(name = "date_finish") val dateFinish: Long,
    val name: String,
    val description: String,
) {
    fun toBusiness(): Business = Business(
        id = id,
        dateStart = dateStart,
        dateFinish = dateFinish,
        name = name,
        description = description,
    )

    companion object {
        fun fromBusinessCreate(businessCreate: BusinessCreate): BusinessDBEntity = BusinessDBEntity(
            id = 0,
            dateStart = businessCreate.dateStart,
            dateFinish = businessCreate.dateFinish,
            name = businessCreate.name,
            description = businessCreate.description,
        )

        fun fromBusiness(business: Business): BusinessDBEntity = BusinessDBEntity(
            id = business.id,
            dateStart = business.dateStart,
            dateFinish = business.dateFinish,
            name = business.name,
            description = business.description,
        )
    }
}
