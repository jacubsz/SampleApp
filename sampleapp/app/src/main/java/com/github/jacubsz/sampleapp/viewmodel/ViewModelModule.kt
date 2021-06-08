package com.github.jacubsz.sampleapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.jacubsz.sampleapp.dagger.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(EmptyViewModel::class)
    abstract fun bindEmptyViewModel(viewModel: EmptyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddNewItemViewModel::class)
    abstract fun bindAddNewItemViewModel(viewModel: AddNewItemViewModel): ViewModel

}