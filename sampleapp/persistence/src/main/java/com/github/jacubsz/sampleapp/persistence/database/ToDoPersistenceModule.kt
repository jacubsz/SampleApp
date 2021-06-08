package com.github.jacubsz.sampleapp.persistence.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ToDoPersistenceModule() {

    @Singleton
    @Provides
    fun provideToDoItemsDatabase(applicationContext: Context) =
        Room.databaseBuilder(
            applicationContext,
            ToDoItemsDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideToDoItemDao(database: ToDoItemsDatabase) =
        database.toDoItemDao()

}