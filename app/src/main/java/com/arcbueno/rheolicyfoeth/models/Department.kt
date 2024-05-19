package com.arcbueno.rheolicyfoeth.models

import java.util.UUID

data class Department(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String? = null
)
// TODO: Add manager
