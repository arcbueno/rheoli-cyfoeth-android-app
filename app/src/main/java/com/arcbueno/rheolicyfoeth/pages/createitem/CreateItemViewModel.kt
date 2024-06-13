package com.arcbueno.rheolicyfoeth.pages.createitem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcbueno.rheolicyfoeth.R
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.models.Item
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository
import com.arcbueno.rheolicyfoeth.repositories.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CreateItemViewModel(
    private val departmentRepository: DepartmentRepository,
    private val itemRepository: ItemRepository
) : ViewModel() {

    init {
        getDepartments()
    }

    private val _uiState = MutableStateFlow(CreateItemState(isLoading = true))
    val state: StateFlow<CreateItemState>
        get() = _uiState.asStateFlow()
    private var initialItem: Item? = null


    fun setInitialData(id: Int): Item {
        val item = runBlocking {

            (viewModelScope.async(Dispatchers.IO) {
                itemRepository.getById(id);
            }).await()
        }
        initialItem = item
        _uiState.value = _uiState.value.copy(isUpdate = true, isLoading = false)

        return item
    }

    private fun getDepartments() {
        viewModelScope.async(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(isLoading = true, isUpdate = initialItem != null)
            val allDepartments = departmentRepository.getAll()
            _uiState.value = _uiState.value.copy(
                departmentList = allDepartments,
                isLoading = false,
                isUpdate = initialItem != null
            )
        }
    }

    fun validate(itemName: String?, department: Department?, quantity: Int): Int? {
        if (itemName.isNullOrEmpty()) {
            return R.string.item_name_is_required_error
        }
        if (department == null && !_uiState.value.isUpdate) {
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
        department: Department?,
        isHidden: Boolean
    ): Boolean {
        return try {
            viewModelScope.launch(Dispatchers.IO) {
                if (state.value.isUpdate) {
                    itemRepository.update(
                        initialItem!!.copy(
                            name = itemName,
                            description = itemDescription.ifEmpty { null },
                            isHidden = isHidden
                        )
                    )
                    return@launch
                }
                itemRepository.create(
                    Item(
                        name = itemName,
                        description = itemDescription,
                        departmentId = department!!.id,
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
    val departmentList: List<Department> = listOf(),
    val isUpdate: Boolean = false,
)