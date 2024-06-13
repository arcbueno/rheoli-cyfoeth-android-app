package com.arcbueno.rheolicyfoeth.repositories

import com.arcbueno.rheolicyfoeth.data.ItemMovingDao
import com.arcbueno.rheolicyfoeth.models.ItemMoving

class ItemMovingRepository(private val itemMovingDao: ItemMovingDao) {
    suspend fun create(department: ItemMoving) {
        itemMovingDao.insert(department)
    }

    suspend fun update(item: ItemMoving) {
        return itemMovingDao.update(item)
    }

    fun getByItemId(id: Int): List<ItemMoving> {
        return itemMovingDao.getByItemId(id)
    }

}