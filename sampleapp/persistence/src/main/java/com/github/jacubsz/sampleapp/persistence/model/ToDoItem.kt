package com.github.jacubsz.sampleapp.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoItem(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "checked") val checked: Boolean?
)