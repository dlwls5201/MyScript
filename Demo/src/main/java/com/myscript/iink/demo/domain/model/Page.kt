package com.myscript.iink.demo.domain.model

data class Page(
    val id: String,
    val notebookId: String,
    val contents: String,
    val createAt: Long,
) {
    val isValidationId get() = id.isNotEmpty()

    companion object {

        val DEFAULT = Page(
            id = "",
            notebookId = "",
            contents = "",
            createAt = System.currentTimeMillis()
        )

        fun create(contents: String): Page {
            return Page(
                id = "",
                notebookId = "",
                contents = contents,
                createAt = System.currentTimeMillis()
            )
        }
    }
}
