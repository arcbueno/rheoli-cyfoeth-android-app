package com.arcbueno.rheolicyfoeth.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keys")
data class Key(
    @PrimaryKey
    val id: Int = 1,
    val value: String,
)