package com.myscript.iink.demo.domain.model


data class Notebook(
    val id: String,
    val title: String,
    val createAt: Long,
) {
    val isValidationId get() = id.isNotEmpty()

    companion object {

        const val DEFAULT_NOTEBOOK_NAME = "Notebook1"

        val DEFAULT = Notebook(
            id = "",
            title = "",
            createAt = System.currentTimeMillis()
        )

        fun create(title: String): Notebook {
            return Notebook(
                id = "",
                title = title,
                createAt = System.currentTimeMillis()
            )
        }
    }
}
