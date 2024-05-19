package com.arcbueno.rheolicyfoeth.repositories

import com.arcbueno.rheolicyfoeth.models.Department

class DepartmentRepository {
    val _departmentList: List<Department> = mutableListOf(
        Department(id = 1, name = "Recepção"),
        Department(id = 2, name = "Financeiro", description = "Pagamentos e impostos"),
        Department(id = 3, name = "Copa", description = "Área para descanço e refeições"),
    )

    fun getAll(): List<Department> {
        return _departmentList
    }

    fun getById(id: Int): Department? {
        if (_departmentList.size > 0) {
            return _departmentList.filter { department: Department -> department.id == id }.first()

        }
        return null
    }
}