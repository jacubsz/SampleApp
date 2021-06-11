package com.github.jacubsz.sampleapp.persistence.plugin

import com.github.jacubsz.sampleapp.contract.datasource.ToDoItemsDataSource
import com.github.jacubsz.sampleapp.persistence.database.ToDoPersistenceModule
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [ToDoPersistenceModule::class])
abstract class AppPersistencePluginModule {

    @Singleton
    @Binds
    abstract fun bindAppPersistencePlugin(appPersistencePlugin: AppPersistencePlugin): ToDoItemsDataSource

}