package com.github.jacubsz.sampleapp.dagger

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.github.jacubsz.sampleapp.viewmodel.AppViewModel
import kotlin.reflect.KClass

fun <VM : AppViewModel> FragmentActivity.obtainViewModel(factory: Lazy<ViewModelProvider.Factory>, viewModelClass: KClass<VM>) =
    lazy { ViewModelProvider(this, factory.value)[viewModelClass.java] }