package com.arcbueno.rheolicyfoeth.repositories

import com.arcbueno.rheolicyfoeth.data.ItemDao
import com.arcbueno.rheolicyfoeth.models.Item

class ItemRepository(val itemDao: ItemDao) {

    suspend fun create(department: Item) {
        itemDao.insert(department)
    }

    suspend fun update(item: Item) {
        return itemDao.update(item)
    }

    suspend fun delete(item: Item) {
        return itemDao.delete(item)
    }

    fun getAll(): List<Item> {
        return itemDao.getAllItems()
    }

    fun getById(id: Int): Item {
        return itemDao.getById(id)
    }

    fun getAllByDepartmentId(departmentId: Int): List<Item> {
        return itemDao.getAllByDepartmentId(departmentId);
    }

}