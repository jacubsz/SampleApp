package com.github.jacubsz.sampleapp.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.jacubsz.sampleapp.persistence.model.ToDoItem
import com.github.jacubsz.sampleapp.persistence.model.ToDoItemDao

const val DATABASE_NAME = "todo-items-database"

@Database(
    version = 2,
    entities = [ToDoItem::class]
)
internal abstract class ToDoItemsDatabase : RoomDatabase() {

    abstract fun toDoItemDao(): ToDoItemDao

}
