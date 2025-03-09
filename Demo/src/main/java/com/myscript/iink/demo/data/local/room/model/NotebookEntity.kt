package com.myscript.iink.demo.data.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myscript.iink.demo.domain.model.Notebook

@Entity
data class NotebookEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "create_at") val createAt: Long
) {
    companion object {

        fun create(title: String): NotebookEntity {
            return NotebookEntity(
                title = title,
                createAt = System.currentTimeMillis()
            )
        }
    }
}

fun NotebookEntity.mapToDomain() = Notebook(
    id = uid.toString(),
    title = title,
    createAt = createAt
)
