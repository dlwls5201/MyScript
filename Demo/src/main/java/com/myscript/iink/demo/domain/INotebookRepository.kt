package com.myscript.iink.demo.domain

import android.content.res.Resources.NotFoundException
import com.myscript.iink.demo.domain.model.Notebook
import com.myscript.iink.demo.domain.model.Page
import kotlinx.coroutines.flow.Flow

interface INotebookRepository {

    /**
     * notebook
     */
    suspend fun createNotebook(title: String): String

    suspend fun removeNotebook(id: String)

    fun getNotebookList(): Flow<List<Notebook>>

    suspend fun getNotebook(id: String): Notebook

    /**
     * page
     */
    @Throws(NotFoundException::class)
    suspend fun createPage(notebookId: String, contents: String): String

    @Throws(NotFoundException::class)
    suspend fun updatePage(
        notebookId: String, pageId: String, contents: String
    )

    suspend fun removePage(id: String)

    fun getPageList(notebookId: String): Flow<List<Page>>

    suspend fun getPage(id: String): Page

    /**
     * delete all data
     */
    suspend fun deleteAllData()
}
