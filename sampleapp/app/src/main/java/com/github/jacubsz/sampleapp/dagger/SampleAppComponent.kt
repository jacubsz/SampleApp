package com.github.jacubsz.sampleapp.dagger

import com.github.jacubsz.sampleapp.SampleApplication
import com.github.jacubsz.sampleapp.persistence.plugin.AppPersistencePluginModule
import com.github.jacubsz.sampleapp.view.ActivityModule
import com.github.jacubsz.sampleapp.viewmodel.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,

        ActivityModule::class,
        ViewModelModule::class,

        AppPersistencePluginModule::class
    ]
)
interface SampleAppComponent : AndroidInjector<SampleApplication> {
    
    @Component.Factory
    interface Factory {
        fun create(): SampleAppComponent
    }
}