package com.example.networking.plugin;

import com.example.networking.AppNetworkingModule;
import com.github.jacubsz.sampleapp.contract.datasource.ToDoItemsDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module(includes = {AppNetworkingModule.class})
public abstract class AppNetworkingPluginModule {

    @Singleton
    @Binds
    abstract ToDoItemsDataSource bindAppNetworkingPlugin(AppNetworkingPlugin appNetworkingPlugin);

}
