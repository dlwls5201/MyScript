package com.myscript.iink.demo.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myscript.iink.demo.data.local.room.model.NotebookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotebookDao {

    @Query("SELECT * FROM NotebookEntity")
    fun getAll(): Flow<List<NotebookEntity>>

    @Query("SELECT * FROM NotebookEntity WHERE uid = :id")
    suspend fun findById(id: Int): NotebookEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserts(entity: NotebookEntity)

    @Query("DELETE FROM NotebookEntity WHERE uid = :id")
    suspend fun deleteById(id: Int)

    @Delete
    suspend fun delete(entity: NotebookEntity)

    @Query("DELETE FROM NotebookEntity")
    suspend fun deleteAll()
}
