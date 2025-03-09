package com.myscript.iink.demo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myscript.iink.demo.domain.INotebookRepository
import com.myscript.iink.demo.domain.model.Notebook
import com.myscript.iink.demo.domain.model.Page
import com.myscript.iink.demo.util.Dlog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotebookViewModel @Inject constructor(
    private val notebookRepository: INotebookRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Dlog.e(throwable.message)
    }

    val notebookItems: Flow<List<Notebook>> = notebookRepository.getNotebookList()

    suspend fun getFirstNotebook(): Notebook {
        val item = notebookItems.firstOrNull()?.firstOrNull() ?: return Notebook.DEFAULT
        return item
    }

    suspend fun createNotebook(title: String): String {
        return notebookRepository.createNotebook(title = title)
    }

    fun getPageItems(notebookId: String): Flow<List<Page>> {
        return notebookRepository.getPageList(notebookId)
    }

    fun deleteAllData() {
        viewModelScope.launch(exceptionHandler) {
            notebookRepository.deleteAllData()
        }
    }
}
