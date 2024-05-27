package com.arcbueno.rheolicyfoeth.pages.itemlist

import androidx.lifecycle.ViewModel
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.models.Item
import com.arcbueno.rheolicyfoeth.repositories.DepartmentRepository
import com.arcbueno.rheolicyfoeth.repositories.ItemRepository

class ItemListViewModel(
    val departmentRepository: DepartmentRepository,
    val itemRepository: ItemRepository
) : ViewModel() {

    fun getAllItems(): List<Item> {
        return itemRepository.getAll();
    }

    fun getDepartmentById(id: Int): Department? {
        return departmentRepository.getById(id);
    }
}