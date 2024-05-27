package com.arcbueno.rheolicyfoeth.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String? = null,
    val isHidden: Boolean = false,
    val quantity: Double = 1.0,
    val departmentId: Int,
)
