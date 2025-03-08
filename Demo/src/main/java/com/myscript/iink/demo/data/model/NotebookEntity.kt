package com.myscript.iink.demo.data.model

import java.util.UUID

data class NotebookEntity private constructor(
    val id: String,
    val title: String,
    val createAt: Long,
) {
    companion object {

        const val DEFAULT_NOTEBOOK_NAME = "Notebook1"

        val DEFAULT = NotebookEntity(
            id = "",
            title = "",
            createAt = System.currentTimeMillis()
        )

        fun create(title: String): NotebookEntity {
            return NotebookEntity(
                id = UUID.randomUUID().toString(),
                title = title,
                createAt = System.currentTimeMillis()
            )
        }
    }
}
