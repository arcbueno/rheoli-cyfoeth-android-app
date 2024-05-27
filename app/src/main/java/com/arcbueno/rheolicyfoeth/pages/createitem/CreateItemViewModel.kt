package com.arcbueno.rheolicyfoeth.pages.createitem

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.models.Item
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository
import com.arcbueno.rheolicyfoeth.repositories.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateItemViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CreateItemState(isLoading = true))
    val state: StateFlow<CreateItemState>
        get() = _uiState.asStateFlow()


    init {
        getDepartments()
    }

    fun getDepartments() {
        val allDepartments = DepartmentRepository.getAll()
        _uiState.value = CreateItemState(
            departmentList = allDepartments
        )
    }

    fun validate(itemName: String?, department: Department?): Int? {
        if (itemName.isNullOrEmpty()) {
            return R.string.item_name_is_required_error
        }
        if (department == null) {
            return R.string.department_is_required_error
        }

        return null;
    }

    fun createItem(itemName: String, itemDescription: String, department: Department): Boolean {
        return try {
            ItemRepository.create(
                Item(
                    name = itemName,
                    description = itemDescription,
                    departmentId = department.id,
                ),
            )
            true;
        } catch (e: Exception) {
            false;
        }
    }
}

data class CreateItemState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val departmentList: List<Department> = listOf()
)