package com.arcbueno.rheolicyfoeth.pages.departmentlist

import androidx.lifecycle.ViewModel
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository

class DepartmentListViewModel(val departmentRepository: DepartmentRepository) : ViewModel() {
    fun getAll(): List<Department> {
        return departmentRepository.getAll();
    }
}