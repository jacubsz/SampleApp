package com.github.jacubsz.sampleapp.persistence.database

import android.content.Context
import androidx.room.Room
import com.github.jacubsz.sampleapp.persistence.database.migration.MIGRATION_1_2
import com.github.jacubsz.sampleapp.persistence.model.ToDoItemDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class ToDoPersistenceModule {

    @Singleton
    @Provides
    fun provideToDoItemsDatabase(applicationContext: Context): ToDoItemsDatabase =
        Room
            .databaseBuilder(
                applicationContext,
                ToDoItemsDatabase::class.java,
                DATABASE_NAME
            )
            .addMigrations(MIGRATION_1_2)
            .build()

    @Singleton
    @Provides
    fun provideToDoItemDao(database: ToDoItemsDatabase): ToDoItemDao =
        database.toDoItemDao()

}