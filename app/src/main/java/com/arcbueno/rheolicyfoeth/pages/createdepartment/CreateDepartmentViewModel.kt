package com.arcbueno.rheolicyfoeth.pages.createdepartment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CreateDepartmentViewModel(
    private val departmentRepository: DepartmentRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateDepartmentState(isLoading = false))
    val state: StateFlow<CreateDepartmentState>
        get() = _uiState.asStateFlow()
    private var initialDepartment: Department? = null

    fun validate(itemName: String?): Int? {
        if (itemName.isNullOrEmpty()) {
            return R.string.department_name_is_required_error
        }

        return null
    }

    fun setInitialData(id: Int): Department {
        val department = runBlocking {

            (viewModelScope.async(Dispatchers.IO) {
                departmentRepository.getById(id);
            }).await()
        }
        initialDepartment = department
        _uiState.value = _uiState.value.copy(isUpdate = true, isLoading = false)

        return department
    }

    fun createDepartment(
        departmentName: String,
        departmentDescription: String,
    ): Boolean {
        return try {
            viewModelScope.launch(Dispatchers.IO) {
                if (state.value.isUpdate) {
                    departmentRepository.update(
                        initialDepartment!!.copy(
                            name = departmentName,
                            description = departmentDescription.ifEmpty { null },
                        )
                    )
                    return@launch
                }
                departmentRepository.create(
                    Department(name = departmentName, description = departmentDescription)
                )
            }
            true
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(error = e.toString())
            false
        }
    }
}

data class CreateDepartmentState(
    val isLoading: Boolean, val error: String? = null,
    val isUpdate: Boolean = false,
)