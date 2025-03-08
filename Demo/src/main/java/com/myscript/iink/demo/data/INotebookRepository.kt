package com.myscript.iink.demo.data

import android.content.res.Resources.NotFoundException
import com.myscript.iink.demo.data.model.NotebookEntity
import com.myscript.iink.demo.data.model.PageEntity
import kotlinx.coroutines.flow.Flow

interface INotebookRepository {

    /**
     * notebook
     */
    suspend fun createNotebook(notebookEntity: NotebookEntity): String

    suspend fun removeNotebook(id: String)

    fun getNotebookList(): Flow<List<NotebookEntity>>

    suspend fun getNotebook(id: String): NotebookEntity?

    /**
     * page
     */
    @Throws(NotFoundException::class)
    suspend fun createPage(pageEntity: PageEntity): String

    @Throws(NotFoundException::class)
    suspend fun updatePage(pageEntity: PageEntity)

    suspend fun removePage(id: String)

    fun getPageList(notebookId: String): Flow<List<PageEntity>>

    suspend fun getPage(id: String): PageEntity?
}
