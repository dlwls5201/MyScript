package com.myscript.iink.demo.data.local.memory.model

import com.myscript.iink.demo.domain.model.Page
import java.util.UUID

data class PageMemory private constructor(
    val id: String,
    val notebookId: String,
    val contents: String,
    val createAt: Long,
) {
    companion object {

        fun create(notebookId: String, contents: String): PageMemory {
            return PageMemory(
                id = UUID.randomUUID().toString(),
                notebookId = notebookId,
                contents = contents,
                createAt = System.currentTimeMillis()
            )
        }
    }
}

fun PageMemory.mapToDomain() = Page(
    id = id,
    notebookId = notebookId,
    contents = contents,
    createAt = createAt
)
