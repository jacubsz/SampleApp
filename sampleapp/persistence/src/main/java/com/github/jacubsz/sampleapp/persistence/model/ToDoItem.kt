package com.github.jacubsz.sampleapp.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class ToDoItem(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "checked") val checked: Boolean?
)