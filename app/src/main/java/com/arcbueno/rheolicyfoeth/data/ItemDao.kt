package com.arcbueno.rheolicyfoeth.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.arcbueno.rheolicyfoeth.models.Item

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): List<Item>

    @Query("SELECT * from items WHERE id = :id")
    fun getById(id: Int): Item

    @Query("SELECT * FROM items WHERE departmentId = :departmentId")
    fun getAllByDepartmentId(departmentId: Int): List<Item>
}