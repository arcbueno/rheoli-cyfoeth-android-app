package com.arcbueno.rheolicyfoeth.pages

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository


private val departmentRepository: DepartmentRepository = DepartmentRepository()

@Composable
fun DepartmentPage() {
    val departmentList = departmentRepository.getAll()
    LazyColumn() {
        items(departmentList) {
            Text(it.name)
            if (it.description != null)
                Text(it.description)
            Divider()
        }
    }
}