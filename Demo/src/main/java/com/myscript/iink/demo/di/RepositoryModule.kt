package com.myscript.iink.demo.di

import com.myscript.iink.demo.data.INotebookRepository
import com.myscript.iink.demo.data.local.MemoryNotebookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNotebookRepository(
        impl: MemoryNotebookRepository,
    ): INotebookRepository
}
