package com.arcbueno.rheolicyfoeth.repositories

import com.arcbueno.rheolicyfoeth.models.Department

class DepartmentRepository {
    private val _departmentList: List<Department> = mutableListOf(
        Department(name = "Recepção"),
        Department(name = "Financeiro", description = "Pagamentos e impostos"),
        Department(name = "Copa", description = "Área para descanço e refeições"),
    )

    fun getAll(): List<Department> {
        return _departmentList
    }

    fun getById(id: String): Department? {
        if (_departmentList.size > 0) {
            return _departmentList.filter { department: Department -> department.id == id }.first()

        }
        return null
    }
}