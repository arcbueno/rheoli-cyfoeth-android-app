package com.arcbueno.rheolicyfoeth.repositories

import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.models.Item

class ItemRepository {
    val _itemList: MutableList<Item> = mutableListOf<Item>(
        Item(
            id = 1,
            name = "Cadeira gamer",
            description = "Financeiro fica querendo coisa estranha",
            departmentId = 2
        )
    )

    fun getAll(): List<Item> {
        return _itemList
    }


    fun getById(id: Int): Item? {
        if (_itemList.size > 0) {
            return _itemList.filter { item: Item -> item.id == id }.first()
        }
        return null
    }

    fun create(item: Item): Unit {
        _itemList.add(item)
    }

    fun update(item: Item): Boolean {
        if (_itemList.any { it.id == item.id }) {
            val old = _itemList.filter { it.id == item.id }.first()
            val index = _itemList.indexOf(old)
            _itemList.set(index, item)
            return true
        }
        return false
    }

    fun delete(item: Item): Unit {
        _itemList.remove(item)
    }

}