package com.arcbueno.rheolicyfoeth.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.arcbueno.rheolicyfoeth.models.Key

@Dao
interface KeyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(key: Key)

    @Update
    suspend fun update(item: Key)

    @Query("SELECT * from keys WHERE id = :id")
    fun getById(id: Int): Key?
}