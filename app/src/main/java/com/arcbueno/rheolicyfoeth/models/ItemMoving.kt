package com.arcbueno.rheolicyfoeth.models

import java.util.Date

data class ItemMoving(
    val startDate: Date?,
    val finishDate: Date,
    val initialDepartment: Department,
    val destinationDepartment: Department,
    val itemId: String,
);
