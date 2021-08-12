package com.github.jacubsz.sampleapp.dagger

import android.content.Context
import com.example.networking.plugin.NetworkingHost
import com.github.jacubsz.sampleapp.BuildConfig
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

    @Singleton
    @Provides
    @NetworkingHost
    fun provideNetworkingHost() = BuildConfig.NETWORKING_HOST

}