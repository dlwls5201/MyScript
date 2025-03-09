package com.myscript.iink.demo.di

import android.content.Context
import androidx.room.Room
import com.myscript.iink.demo.data.local.room.AppDatabase
import com.myscript.iink.demo.data.local.room.dao.NotebookDao
import com.myscript.iink.demo.data.local.room.dao.PageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideNotebookDao(database: AppDatabase): NotebookDao {
        return database.notebookDao()
    }

    @Provides
    fun providePageDao(database: AppDatabase): PageDao {
        return database.pageDao()
    }
}
