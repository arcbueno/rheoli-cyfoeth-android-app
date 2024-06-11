package com.arcbueno.rheolicyfoeth.pages.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcbueno.rheolicyfoeth.pages.itemlist.ItemListState
import com.arcbueno.rheolicyfoeth.repositories.KeyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SettingsViewModel(val keyRepository: KeyRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsState(isLoading = true))
    val state: StateFlow<SettingsState>
        get() = _uiState.asStateFlow()

    init {
        getKey()
    }

    fun getKey() {
        val result = runBlocking {
            (viewModelScope.async(Dispatchers.IO) {
                keyRepository.getById(1)
            }).await()
        }
        _uiState.value = _uiState.value.copy(key = result?.value)

    }

    fun onChangeKey(newKey: String): Boolean {
        val result = runBlocking {
            (viewModelScope.async(Dispatchers.IO) {
                keyRepository.getById(1)
            }).await()
        } ?: return false
        var key = result.copy(value = newKey)

        return runBlocking {
            (viewModelScope.async(Dispatchers.IO) {
                keyRepository.update(key)
            }).await()
            true
        }

    }
}

data class SettingsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val key: String? = null
)