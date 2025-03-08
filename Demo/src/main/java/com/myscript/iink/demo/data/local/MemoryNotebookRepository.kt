package com.myscript.iink.demo.data.local

import android.content.res.Resources.NotFoundException
import com.myscript.iink.demo.data.INotebookRepository
import com.myscript.iink.demo.data.model.NotebookEntity
import com.myscript.iink.demo.data.model.PageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class MemoryNotebookRepository : INotebookRepository {

    private val notebookItems = MutableStateFlow<List<NotebookEntity>>(emptyList())

    private val pageItems = MutableStateFlow<List<PageEntity>>(emptyList())

    override suspend fun createNotebook(notebookEntity: NotebookEntity) {
        notebookItems.value += notebookEntity
    }

    override suspend fun removeNotebook(id: Int) {
        val foundItem = notebookItems.value.find { it.id == id }
        if (foundItem != null) {
            notebookItems.value -= foundItem
        }
    }

    override fun getNotebookList(): Flow<List<NotebookEntity>> {
        return notebookItems.asStateFlow()
    }

    @Throws(NotFoundException::class)
    override suspend fun createPage(pageEntity: PageEntity) {
        val notebookId = pageEntity.notebookId
        val notebookItem = notebookItems.value.find { it.id == notebookId }
        if (notebookItem == null) {
            throw NotFoundException("Notebook not found")
        }

        pageItems.value += pageEntity
    }

    override suspend fun removePage(id: Int) {
        val foundItem = pageItems.value.find { it.id == id }
        if (foundItem != null) {
            pageItems.value -= foundItem
        }
    }

    override fun getPageList(notebookId: Int): Flow<List<PageEntity>> {
        return pageItems
            .asStateFlow()
            .map { pages -> pages.filter { it.notebookId == notebookId } }
    }

    override suspend fun getPage(id: Int): PageEntity? {
        val foundItem = pageItems.value.find { it.id == id }
        return foundItem
    }
}
