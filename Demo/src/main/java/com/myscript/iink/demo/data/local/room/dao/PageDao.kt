package com.myscript.iink.demo.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myscript.iink.demo.data.local.room.model.PageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PageDao {

    @Query("SELECT * FROM PageEntity WHERE notebook_id = :notebookId")
    fun getAllByNotebookId(notebookId: Int): Flow<List<PageEntity>>

    @Query("SELECT * FROM PageEntity WHERE uid = :id")
    suspend fun findById(id: Int): PageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserts(entity: PageEntity)

    @Query("DELETE FROM PageEntity WHERE uid = :id")
    suspend fun deleteById(id: Int)

    @Delete
    suspend fun delete(entity: PageEntity)

    @Query("DELETE FROM PageEntity")
    suspend fun deleteAll()
}
