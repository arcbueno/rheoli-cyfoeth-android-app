package com.arcbueno.rheolicyfoeth.pages.itemdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.models.Item
import com.arcbueno.rheolicyfoeth.models.ItemMoving
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository
import com.arcbueno.rheolicyfoeth.repositories.ItemMovingRepository
import com.arcbueno.rheolicyfoeth.repositories.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ItemDetailViewModel(
    val itemRepository: ItemRepository,
    val itemMovingRepository: ItemMovingRepository,
    val departmentRepository: DepartmentRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ItemDetailState())
    val state: StateFlow<ItemDetailState>
        get() = _uiState.asStateFlow()

    fun setInitialData(itemId: Int) {

        _uiState.value = _uiState.value.copy(isLoading = true)
        val item = runBlocking {
            (viewModelScope.async(Dispatchers.IO) {
                itemRepository.getById(itemId)
            }).await()
        }
        _uiState.value = _uiState.value.copy(item = item)
        getMovings(item)
        getDepartments()
    }

    fun getMovings(item: Item) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val movings = itemMovingRepository.getByItemId(item.id)
            _uiState.value = _uiState.value.copy(movings = movings)
        }
    }

    fun getDepartments() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val departments = departmentRepository.getAll()
            _uiState.value = _uiState.value.copy(departmentList = departments)
        }
    }

    fun getDepartmentById(id: Int): Department {
        return state.value.departmentList.single { it.id == id }
    }

    fun onNewMoving(itemMoving: ItemMoving) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        runBlocking {
            viewModelScope.launch(Dispatchers.IO) {
                itemMovingRepository.create(itemMoving)
                val currentItem =
                    _uiState.value.item!!.copy(departmentId = itemMoving.destinationDepartmentId)
                itemRepository.update(currentItem)
                _uiState.value = _uiState.value.copy(item = currentItem)
            }
            getMovings(_uiState.value.item!!)
            getDepartments()
        }


    }

}


data class ItemDetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val departmentList: List<Department> = listOf(),
    val item: Item? = null,
    val movings: List<ItemMoving> = listOf(),
)