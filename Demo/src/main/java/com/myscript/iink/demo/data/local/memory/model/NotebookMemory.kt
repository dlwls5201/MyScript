package com.myscript.iink.demo.data.local.memory.model

import com.myscript.iink.demo.domain.model.Notebook
import java.util.UUID

data class NotebookMemory private constructor(
    val id: String,
    val title: String,
    val createAt: Long,
) {
    companion object {

        fun create(title: String): NotebookMemory {
            return NotebookMemory(
                id = UUID.randomUUID().toString(),
                title = title,
                createAt = System.currentTimeMillis()
            )
        }
    }
}

fun NotebookMemory.mapToDomain() = Notebook(
    id = id,
    title = title,
    createAt = createAt
)
