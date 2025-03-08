package com.myscript.iink.demo.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PageModel(
    val notebookId: Int,
    val pageContent: PageContentsModel,
): Parcelable {

    companion object {

        val EMPTY = PageModel(
            notebookId = -1,
            pageContent = PageContentsModel()
        )
    }
}

@Parcelize
data class PageContentsModel(
    val contents: String = "",
): Parcelable
