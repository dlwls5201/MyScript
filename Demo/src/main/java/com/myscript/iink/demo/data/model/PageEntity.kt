package com.myscript.iink.demo.data.model

data class PageEntity(
    val id: Int,
    val notebookId: Int,
    val contents: String,
    val createAt: Long,
)
