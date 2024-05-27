package com.arcbueno.rheolicyfoeth.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.arcbueno.rheolicyfoeth.models.Department
import com.arcbueno.rheolicyfoeth.models.ItemMoving
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemMovingDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(itemMoving: ItemMoving)

    @Update
    suspend fun update(itemMoving: ItemMoving)

    @Delete
    suspend fun delete(itemMoving: ItemMoving)

    @Query("SELECT * from item_moving WHERE itemId = :id")
    fun getByItemId(id: Int): List<ItemMoving>
}