package com.github.jacubsz.sampleapp

import com.github.jacubsz.sampleapp.dagger.AppModule
import com.github.jacubsz.sampleapp.dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class SampleApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent
            .factory()
            .create(AppModule(applicationContext))
            .also { it.inject(this) }
}