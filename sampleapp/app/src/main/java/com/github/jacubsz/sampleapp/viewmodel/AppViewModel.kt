package com.github.jacubsz.sampleapp.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * Superclass for every ViewModel in the app.
 * Handles Network availability changes and disposables
 */
abstract class AppViewModel : ViewModel() {

    /**
     * Use it for ui/non-network subscriptions only
     */
    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        destroy()
        super.onCleared()
    }

    fun destroy() {
        disposables.clear()
    }

    abstract fun init()

}