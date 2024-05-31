package com.arcbueno.rheolicyfoeth.repositories

import com.arcbueno.rheolicyfoeth.data.DepartmentDao
import com.arcbueno.rheolicyfoeth.data.KeyDao
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.models.Key
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KeyRepository(val keyDao: KeyDao) {
    init {
        CoroutineScope(Dispatchers.IO).launch {
            val key = keyDao.getById(1)
            if (key == null) {
                keyDao.insert(Key(id = 1, value = "12345"))
            }
        }

    }

    fun getById(id: Int): Key? {
        return keyDao.getById(id)
    }

    suspend fun update(key: Key) {
        return keyDao.update(key)
    }
}
