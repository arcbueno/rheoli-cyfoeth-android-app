package com.arcbueno.rheolicyfoeth.repositories

import com.arcbueno.rheolicyfoeth.data.DepartmentDao
import com.arcbueno.rheolicyfoeth.models.Department
import kotlinx.coroutines.*

class DepartmentRepository(val departmentDao: DepartmentDao) {


    init {
        CoroutineScope(Dispatchers.IO).launch {
            val allDepartments = getAll()
            if (allDepartments.isEmpty()) {
                var depList = listOf(
                    Department(name = "Recepção"),
                    Department(name = "Financeiro", description = "Pagamentos e impostos"),
                    Department(name = "Copa", description = "Área para descanço e refeições"),
                )

                for (dep in depList) {
                    create(dep)
                }
            }
        }


    }

    suspend fun create(department: Department) {
        departmentDao.insert(department)
    }

    fun getAll(): List<Department> {
        return departmentDao.getAllDepartments()
    }

    fun getById(id: Int): Department? {
        return departmentDao.getById(id)
    }
}