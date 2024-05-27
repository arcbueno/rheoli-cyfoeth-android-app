package com.arcbueno.rheolicyfoeth.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "item_moving")
data class ItemMoving(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val startDate: LocalDateTime?,
    val finishDate: LocalDateTime,
    val initialDepartmentId: Int,
    val destinationDepartmentId: Int,
    val itemId: String,
);
