package com.myscript.iink.demo.data.model

import java.util.UUID

data class PageEntity private constructor(
    val id: String,
    val notebookId: String,
    val contents: String,
    val createAt: Long,
) {
    companion object {

        fun create(notebookId: String, contents: String): PageEntity {
            return PageEntity(
                id = UUID.randomUUID().toString(),
                notebookId = notebookId,
                contents = contents,
                createAt = System.currentTimeMillis()
            )
        }
    }
}
