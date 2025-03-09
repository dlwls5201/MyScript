package com.myscript.iink.demo.data.local.memory

import android.content.res.Resources.NotFoundException
import com.myscript.iink.demo.data.local.memory.model.NotebookMemory
import com.myscript.iink.demo.data.local.memory.model.PageMemory
import com.myscript.iink.demo.data.local.memory.model.mapToDomain
import com.myscript.iink.demo.domain.INotebookRepository
import com.myscript.iink.demo.domain.model.Notebook
import com.myscript.iink.demo.domain.model.Page
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryNotebookRepository @Inject constructor() : INotebookRepository {

    private val notebookItems = MutableStateFlow<List<Notebook>>(emptyList())

    private val pageItems = MutableStateFlow<List<Page>>(emptyList())

    override suspend fun createNotebook(title: String): String {
        val newNotebook = NotebookMemory.create(title).mapToDomain()
        notebookItems.value += newNotebook
        return newNotebook.id
    }

    override suspend fun removeNotebook(id: String) {
        val foundItem = notebookItems.value.find { it.id == id }
        if (foundItem != null) {
            notebookItems.value -= foundItem
        }
    }

    override fun getNotebookList(): Flow<List<Notebook>> {
        return notebookItems.asStateFlow()
    }

    override suspend fun getNotebook(id: String): Notebook {
        val foundItem = notebookItems.value.find { it.id == id }
        return foundItem ?: Notebook.DEFAULT
    }

    @Throws(NotFoundException::class)
    override suspend fun createPage(notebookId: String, contents: String): String {
        val notebookItem = notebookItems.value.find { it.id == notebookId }
        if (notebookItem == null) {
            throw NotFoundException("Notebook not found")
        }

        val newPage = PageMemory.create(notebookId, contents).mapToDomain()
        pageItems.value += newPage
        return newPage.id
    }


    override suspend fun updatePage(notebookId: String, pageId: String, contents: String) {
        val notebookItem = notebookItems.value.find { it.id == notebookId }
        if (notebookItem == null) {
            throw NotFoundException("Notebook not found")
        }

        val foundItemIndex = pageItems.value.indexOfFirst { it.id == pageId }
        if (foundItemIndex == -1) {
            createPage(notebookId = notebookId, contents = contents)
            return
        }

        val updatedPages = pageItems.value.toMutableList()
        val newPage = PageMemory.create(notebookId, contents).mapToDomain()
        updatedPages[foundItemIndex] = newPage

        pageItems.value = updatedPages
    }

    override suspend fun removePage(id: String) {
        val foundItem = pageItems.value.find { it.id == id }
        if (foundItem != null) {
            pageItems.value -= foundItem
        }
    }

    override fun getPageList(notebookId: String): Flow<List<Page>> {
        return pageItems
            .asStateFlow()
            .map { pages -> pages.filter { it.notebookId == notebookId } }
    }

    override suspend fun getPage(id: String): Page {
        val foundItem = pageItems.value.find { it.id == id }
        return foundItem ?: Page.DEFAULT
    }
}
