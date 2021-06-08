package com.github.jacubsz.sampleapp.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
    private val applicationContext: Context
) {

    @Singleton
    @Provides
    fun provideApplicationContext(): Context = applicationContext

}