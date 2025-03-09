package com.myscript.iink.demo.domain.model


data class Notebook(
    val id: String,
    val title: String,
    val createAt: Long,
) {
    val isValidationId get() = id.isNotEmpty()

    companion object {

        val DEFAULT = Notebook(
            id = "",
            title = "",
            createAt = System.currentTimeMillis()
        )

        fun create(id: String = "", title: String): Notebook {
            return Notebook(
                id = id,
                title = title,
                createAt = System.currentTimeMillis()
            )
        }
    }
}
