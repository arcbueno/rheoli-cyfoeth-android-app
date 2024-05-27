package com.arcbueno.rheolicyfoeth.pages.departmentlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DepartmentListViewModel(val departmentRepository: DepartmentRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(DepartmentListState(isLoading = true))
    val state: StateFlow<DepartmentListState>
        get() = _uiState.asStateFlow()

//    init {
//        getAll()
//    }


    fun getAll(): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            val lista = departmentRepository.getAll()
            _uiState.value = _uiState.value.copy(
                departmentList = lista
            )
        }

    }
}


data class DepartmentListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val departmentList: List<Department> = listOf()
)

