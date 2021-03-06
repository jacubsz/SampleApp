package com.github.jacubsz.sampleapp.view

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeAddNewToDoItemActivity(): AddNewToDoItemActivity

    @ContributesAndroidInjector
    abstract fun contributeToDoListActivity(): ToDoListActivity

    @ContributesAndroidInjector
    abstract fun contributeAboutActivity(): AboutActivity

}