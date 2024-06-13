package com.arcbueno.rheolicyfoeth.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.models.Item
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository
import com.arcbueno.rheolicyfoeth.repositories.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val departmentRepository: DepartmentRepository,
    private val itemRepository: ItemRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState(isLoading = true))
    val state: StateFlow<HomeState>
        get() = _uiState.asStateFlow()

    init {
        getDepartments()
        getItems()
    }

    private fun startLoading() {
        _uiState.value = _uiState.value.copy(isLoading = true)
    }

    private fun getDepartments() {
        startLoading()
        viewModelScope.launch(Dispatchers.IO) {
            val lista = departmentRepository.getAll()
            _uiState.value = _uiState.value.copy(
                departmentList = lista
            )
        }
    }

    private fun getItems() {
        startLoading()
        viewModelScope.launch(Dispatchers.IO) {
            val allItems = itemRepository.getAll()
            _uiState.value = _uiState.value.copy(isLoading = false, itemList = allItems)
        }

    }
}

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val departmentList: List<Department> = listOf(),
    val itemList: List<Item> = listOf(),
)