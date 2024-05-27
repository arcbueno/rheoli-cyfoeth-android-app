package com.arcbueno.rheolicyfoeth.models

import java.util.UUID

data class Item(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String? = null,
    val isHidden: Boolean = false,
    val quantity: Double = 1.0,
    val departmentId: String,
)
