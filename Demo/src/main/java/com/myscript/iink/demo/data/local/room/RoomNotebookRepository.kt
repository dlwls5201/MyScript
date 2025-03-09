package com.myscript.iink.demo.data.local.room

import android.content.res.Resources.NotFoundException
import com.myscript.iink.demo.data.local.room.dao.NotebookDao
import com.myscript.iink.demo.data.local.room.dao.PageDao
import com.myscript.iink.demo.data.local.room.model.NotebookEntity
import com.myscript.iink.demo.data.local.room.model.PageEntity
import com.myscript.iink.demo.data.local.room.model.mapToDomain
import com.myscript.iink.demo.domain.INotebookRepository
import com.myscript.iink.demo.domain.model.Notebook
import com.myscript.iink.demo.domain.model.Page
import com.myscript.iink.demo.util.Dlog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomNotebookRepository @Inject constructor(
    private val notebookDao: NotebookDao,
    private val pageDao: PageDao,
) : INotebookRepository {

    override suspend fun createNotebook(title: String): String {
        val newNotebook = NotebookEntity.create(title = title)
        notebookDao.inserts(newNotebook)
        return newNotebook.uid.toString()
    }

    override suspend fun removeNotebook(id: String) {
        try {
            notebookDao.deleteById(id.toInt())
        } catch (e: Exception) {
            Dlog.d(e.message)
        }
    }

    override fun getNotebookList(): Flow<List<Notebook>> {
        return notebookDao
            .getAll()
            .map { notebookEntities ->
                notebookEntities.map { it.mapToDomain() }
            }
    }

    override suspend fun getNotebook(id: String): Notebook {
        return try {
            notebookDao.findById(id.toInt())?.mapToDomain() ?: Notebook.DEFAULT
        } catch (e: Exception) {
            Dlog.d(e.message)
            Notebook.DEFAULT
        }
    }

    @Throws(NotFoundException::class)
    override suspend fun createPage(notebookId: String, contents: String): String {
        try {
            notebookDao.findById(notebookId.toInt()) ?: throw NotFoundException("Notebook not found")
        } catch (e: Exception) {
            throw NotFoundException(e.message)
        }

        val newPage = PageEntity.create(notebookId, contents)
        pageDao.inserts(newPage)

        return newPage.uid.toString()
    }

    @Throws(NotFoundException::class)
    override suspend fun updatePage(notebookId: String, pageId: String, contents: String) {
        try {
            notebookDao.findById(notebookId.toInt()) ?: throw NotFoundException("Notebook not found")
        } catch (e: Exception) {
            throw NotFoundException(e.message)
        }

        val foundPage = pageDao.findById(pageId.toInt())
        if (foundPage == null) {
            createPage(notebookId, contents)
            return
        }

        val copyPage = foundPage.copy(contents = contents)
        pageDao.inserts(copyPage)
    }

    override suspend fun removePage(id: String) {
        try {
            pageDao.deleteById(id.toInt())
        } catch (e: Exception) {
            Dlog.d(e.message)
        }
    }

    override fun getPageList(notebookId: String): Flow<List<Page>> {
        if (notebookId.isBlank()) {
            return emptyFlow()
        }

        return pageDao
            .getAllByNotebookId(notebookId.toInt())
            .map { pageEntities ->
                pageEntities.map { it.mapToDomain() }
            }
    }

    override suspend fun getPage(id: String): Page {
        return try {
            pageDao.findById(id.toInt())?.mapToDomain() ?: Page.DEFAULT
        } catch (e: Exception) {
            Dlog.d(e.message)
            Page.DEFAULT
        }
    }
}
