package com.myscript.iink.demo.data

import android.content.res.Resources.NotFoundException
import com.myscript.iink.demo.data.model.NotebookEntity
import com.myscript.iink.demo.data.model.PageEntity
import kotlinx.coroutines.flow.Flow

interface INotebookRepository {

    /**
     * notebook
     */
    suspend fun createNotebook(notebookEntity: NotebookEntity)

    suspend fun removeNotebook(id: Int)

    fun getNotebookList(): Flow<List<NotebookEntity>>

    /**
     * page
     */
    @Throws(NotFoundException::class)
    suspend fun createPage(pageEntity: PageEntity)

    suspend fun removePage(id: Int)

    fun getPageList(notebookId: Int): Flow<List<PageEntity>>

    suspend fun getPage(id: Int): PageEntity?
}
