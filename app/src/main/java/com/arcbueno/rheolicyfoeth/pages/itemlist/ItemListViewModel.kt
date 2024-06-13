package com.arcbueno.rheolicyfoeth.pages.itemlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.models.Item
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository
import com.arcbueno.rheolicyfoeth.repositories.ItemRepository
import com.arcbueno.rheolicyfoeth.repositories.KeyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ItemListViewModel(
    private val departmentRepository: DepartmentRepository,
    private val itemRepository: ItemRepository,
    private val keyRepository: KeyRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ItemListState(isLoading = true))
    val state: StateFlow<ItemListState>
        get() = _uiState.asStateFlow()

    fun showAll(inputKey: String): Boolean {
        var key = ""
        val result = runBlocking {
            (viewModelScope.async(Dispatchers.IO) {
                keyRepository.getById(1)
            }).await()
        }
        key = result?.value ?: ""

        if (inputKey == key) {
            _uiState.value = _uiState.value.copy(showAll = true)
            getAllItems(true)
            return true

        }
        return false
    }

    fun getAllItems(showAll: Boolean = false): Unit {
        var itemList = listOf<Item>()
        viewModelScope.launch(Dispatchers.IO) {
            itemList = itemRepository.getAll();
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                itemList = if (showAll) itemList else itemList.filter { !it.isHidden })
        }
    }

    fun getDepartmentById(id: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            val dep = departmentRepository.getById(id)
            
            _uiState.value.departmentById[id] = dep
            _uiState.value = _uiState.value.copy(departmentById = _uiState.value.departmentById)
        }
    }

    fun hideItens() {
        _uiState.value = _uiState.value.copy(showAll = false)
        getAllItems(false)
    }
}

data class ItemListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val showAll: Boolean = false,
    val itemList: List<Item> = listOf<Item>(),
    val departmentById: MutableMap<Int, Department> = mutableMapOf<Int, Department>()
)