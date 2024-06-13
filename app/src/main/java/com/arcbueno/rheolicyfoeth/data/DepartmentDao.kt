package com.arcbueno.rheolicyfoeth.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.arcbueno.rheolicyfoeth.models.Department

@Dao
interface DepartmentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(department: Department)

    @Update
    suspend fun update(department: Department)

    @Delete
    suspend fun delete(department: Department)

    @Query("SELECT * from departments ORDER BY name ASC")
    fun getAllDepartments(): List<Department>

    @Query("SELECT * from departments WHERE id = :id")
    fun getById(id: Int): Department
}