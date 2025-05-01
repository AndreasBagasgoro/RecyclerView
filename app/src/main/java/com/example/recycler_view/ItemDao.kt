package com.example.recycler_view.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface ItemDao {
    @Query("SELECT * FROM items ORDER BY id ASC")
    suspend fun getAll(): List<Item>

    @Insert
    suspend fun insert(item: Item): Long

    @Delete
    suspend fun delete(item: Item)
}


