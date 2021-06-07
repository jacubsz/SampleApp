package com.github.jacubsz.sampleapp

import com.github.jacubsz.sampleapp.dagger.DaggerSampleAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class SampleApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerSampleAppComponent
            .factory()
            .create()
            .also { it.inject(this) }
}