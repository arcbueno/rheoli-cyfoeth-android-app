package com.arcbueno.rheolicyfoeth.pages.departmentdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.models.Item
import com.arcbueno.rheolicyfoeth.pages.createdepartment.CreateDepartmentState
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository
import com.arcbueno.rheolicyfoeth.repositories.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DepartmentDetailViewModel(
    private val departmentRepository: DepartmentRepository,
    private val itemRepository: ItemRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DepartmentDetailState())
    val state: StateFlow<DepartmentDetailState>
        get() = _uiState.asStateFlow()

    // TODO: Show hidden items only if password was inserted

    fun setInitialData(departmentId: Int) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        val department = runBlocking {
            (viewModelScope.async(Dispatchers.IO) {
                departmentRepository.getById(departmentId)
            }).await()
        }
        _uiState.value = _uiState.value.copy(department = department)
        getItems(departmentId)
    }

    fun getItems(departmentId: Int) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val items = itemRepository.getAllByDepartmentId(departmentId)
            _uiState.value = _uiState.value.copy(itemList = items)
        }
    }

}

data class DepartmentDetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val itemList: List<Item> = listOf<Item>(),
    val department: Department? = null
)