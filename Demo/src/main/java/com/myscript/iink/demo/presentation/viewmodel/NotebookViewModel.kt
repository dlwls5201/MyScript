package com.myscript.iink.demo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.myscript.iink.demo.data.INotebookRepository
import com.myscript.iink.demo.data.model.NotebookEntity
import com.myscript.iink.demo.data.model.PageEntity
import com.myscript.iink.demo.util.Dlog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class NotebookViewModel @Inject constructor(
    private val notebookRepository: INotebookRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Dlog.e(throwable.message)
    }

    val notebookItems: Flow<List<NotebookEntity>> = notebookRepository.getNotebookList()

    suspend fun getFirstNotebook(): NotebookEntity {
        val item = notebookItems.firstOrNull()?.firstOrNull()
        if (item == null) {
            val notebookId = createDefaultNotebook()
            return notebookRepository.getNotebook(notebookId) ?: NotebookEntity.DEFAULT
        }
        return item
    }

    private suspend fun createDefaultNotebook(): String {
        return createNotebook(NotebookEntity.DEFAULT_NOTEBOOK_NAME)
    }

    suspend fun createNotebook(title: String): String {
        return notebookRepository.createNotebook(
            NotebookEntity.create(title = title)
        )
    }

    fun getPageItems(notebookId: String): Flow<List<PageEntity>> {
        return notebookRepository.getPageList(notebookId)
    }
}
