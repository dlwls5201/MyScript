package com.myscript.iink.demo.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myscript.iink.demo.data.GeminiRepository
import com.myscript.iink.demo.di.AppScope
import com.myscript.iink.demo.domain.INotebookRepository
import com.myscript.iink.demo.domain.model.Page
import com.myscript.iink.demo.presentation.WritingActivity
import com.myscript.iink.demo.util.Dlog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class WritingType {
    NEW, UPDATE
}

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

    private val _pageFlow = MutableStateFlow(Page.DEFAULT)
    val pageFlow = _pageFlow.asStateFlow()

    private val writingType
        get() = if (pageId.isBlank()) {
            WritingType.NEW
        } else {
            WritingType.UPDATE
        }

    fun isValidNotebookId(): Boolean {
        return notebookId.isNotBlank()
    }

    fun initPage(clearContents: () -> Unit = {}) {
        viewModelScope.launch(exceptionHandler) {
            val page = notebookRepository.getPage(pageId)
            if (page.isValidationId) {
                _pageFlow.value = page
            } else {
                clearContents()
            }
        }
    }

    fun savePage(contents: String) {
        if (contents.isBlank()) {
            return
        }

        appScope.launch(exceptionHandler) {
            when (writingType) {
                WritingType.NEW -> {
                    notebookRepository.createPage(notebookId = notebookId, contents = contents)
                }

                WritingType.UPDATE -> {
                    notebookRepository.updatePage(
                        notebookId = notebookId, pageId = pageId, contents = contents
                    )
                }
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
