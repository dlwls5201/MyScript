package com.myscript.iink.demo.data.local

import android.content.res.Resources.NotFoundException
import com.myscript.iink.demo.data.INotebookRepository
import com.myscript.iink.demo.data.model.NotebookEntity
import com.myscript.iink.demo.data.model.PageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryNotebookRepository @Inject constructor() : INotebookRepository {

    private val notebookItems = MutableStateFlow<List<NotebookEntity>>(emptyList())

    private val pageItems = MutableStateFlow<List<PageEntity>>(emptyList())

    override suspend fun createNotebook(notebookEntity: NotebookEntity): String {
        notebookItems.value += notebookEntity
        return notebookEntity.id
    }

    override suspend fun removeNotebook(id: String) {
        val foundItem = notebookItems.value.find { it.id == id }
        if (foundItem != null) {
            notebookItems.value -= foundItem
        }
    }

    override fun getNotebookList(): Flow<List<NotebookEntity>> {
        return notebookItems.asStateFlow()
    }

    override suspend fun getNotebook(id: String): NotebookEntity? {
        val foundItem = notebookItems.value.find { it.id == id }
        return foundItem
    }

    @Throws(NotFoundException::class)
    override suspend fun createPage(pageEntity: PageEntity): String {
        val notebookId = pageEntity.notebookId
        val notebookItem = notebookItems.value.find { it.id == notebookId }
        if (notebookItem == null) {
            throw NotFoundException("Notebook not found")
        }

        pageItems.value += pageEntity
        return pageEntity.id
    }

    override suspend fun removePage(id: String) {
        val foundItem = pageItems.value.find { it.id == id }
        if (foundItem != null) {
            pageItems.value -= foundItem
        }
    }

    override fun getPageList(notebookId: String): Flow<List<PageEntity>> {
        return pageItems
            .asStateFlow()
            .map { pages -> pages.filter { it.notebookId == notebookId } }
    }

    override suspend fun getPage(id: String): PageEntity? {
        val foundItem = pageItems.value.find { it.id == id }
        return foundItem
    }
}
