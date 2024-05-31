package com.arcbueno.rheolicyfoeth.pages.createitem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.models.Item
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository
import com.arcbueno.rheolicyfoeth.repositories.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateItemViewModel(
    private val departmentRepository: DepartmentRepository,
    private val itemRepository: ItemRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateItemState(isLoading = true))
    val state: StateFlow<CreateItemState>
        get() = _uiState.asStateFlow()

    init {
        getDepartments()
    }

    private fun getDepartments() {
        viewModelScope.launch(Dispatchers.IO) {
            val allDepartments = departmentRepository.getAll()
            _uiState.value = CreateItemState(
                departmentList = allDepartments
            )
        }
    }

    fun validate(itemName: String?, department: Department?, quantity: Int): Int? {
        if (itemName.isNullOrEmpty()) {
            return R.string.item_name_is_required_error
        }
        if (department == null) {
            return R.string.department_is_required_error
        }
        if (quantity == 0) {
            return R.string.quantity_is_required_error
        }

        return null
    }

    fun createItem(
        itemName: String,
        itemDescription: String,
        department: Department,
        isHidden: Boolean
    ): Boolean {
        return try {
            viewModelScope.launch(Dispatchers.IO) {
                itemRepository.create(
                    Item(
                        name = itemName,
                        description = itemDescription,
                        departmentId = department.id,
                        isHidden = isHidden,
                    ),
                )
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}

data class CreateItemState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val departmentList: List<Department> = listOf()
)