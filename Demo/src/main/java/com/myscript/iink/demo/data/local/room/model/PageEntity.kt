package com.myscript.iink.demo.data.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myscript.iink.demo.domain.model.Page

@Entity
data class PageEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "notebook_id") val notebookId: Int,
    @ColumnInfo(name = "contents") val contents: String,
    @ColumnInfo(name = "create_at") val createAt: Long
) {
    companion object {

        fun create(notebookId: String, contents: String): PageEntity {
            return PageEntity(
                notebookId = notebookId.toInt(),
                contents = contents,
                createAt = System.currentTimeMillis()
            )
        }
    }
}

fun PageEntity.mapToDomain() = Page(
    id = uid.toString(),
    notebookId = notebookId.toString(),
    contents = contents,
    createAt = createAt
)
