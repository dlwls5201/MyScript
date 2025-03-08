package com.myscript.iink.demo.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myscript.iink.demo.data.GeminiRepository
import com.myscript.iink.demo.data.INotebookRepository
import com.myscript.iink.demo.data.model.PageEntity
import com.myscript.iink.demo.di.AppScope
import com.myscript.iink.demo.presentation.WritingActivity
import com.myscript.iink.demo.util.Dlog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WritingViewModel @Inject constructor(
    private val geminiRepository: GeminiRepository,
    private val notebookRepository: INotebookRepository,
    @AppScope private val appScope: CoroutineScope,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Dlog.e(throwable.message)
    }

    private val pageId = savedStateHandle.get<String>(WritingActivity.PARAM_PAGE_ID) ?: ""
    private val notebookId = savedStateHandle.get<String>(WritingActivity.PARAM_NOTEBOOK_ID) ?: ""

    private val _pageFlow = MutableStateFlow(PageEntity.DEFAULT)
    val pageFlow = _pageFlow.asStateFlow()

    fun isValidNotebookId(): Boolean {
        return notebookId.isNotBlank()
    }

    fun initPage() {
        viewModelScope.launch(exceptionHandler) {
            notebookRepository.getPage(pageId)?.let {
                _pageFlow.value = it
            }
        }
    }

    fun savePage(contents: String) {
        if (contents.isBlank()) {
            return
        }

        appScope.launch(exceptionHandler) {
            if (pageId.isBlank()) {
                val page = PageEntity.create(notebookId, contents)
                notebookRepository.createPage(page)
            } else {
                val page = _pageFlow.value.copy(
                    contents = contents
                )
                notebookRepository.updatePage(page)
            }
        }
    }

    suspend fun generatePredictText(text: String): String {
        return try {
            geminiRepository.generateText(text)
        } catch (e: Exception) {
            Dlog.e(e.message)
            text
        }
    }
}
