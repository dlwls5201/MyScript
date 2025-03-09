package com.myscript.iink.demo.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myscript.iink.demo.data.local.room.dao.NotebookDao
import com.myscript.iink.demo.data.local.room.dao.PageDao
import com.myscript.iink.demo.data.local.room.model.NotebookEntity
import com.myscript.iink.demo.data.local.room.model.PageEntity

@Database(
    entities = [NotebookEntity::class, PageEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun notebookDao(): NotebookDao

    abstract fun pageDao(): PageDao
}
