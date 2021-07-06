package com.github.jacubsz.sampleapp.viewmodel

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify
import java.util.concurrent.TimeUnit

class AppViewModelTest {

    private val testViewModel = object : AppViewModel() {

        override fun init() {}

        fun getCompositeDisposables() = disposables

        fun callOnCleared() = onCleared()

    }

    @Test
    fun `onCleared clears observables and disposes them`() {
        val testScheduler = TestScheduler()
        val testViewModelSpy = Mockito.spy(testViewModel)
        val testDisposable = Observable.just(Unit)
            .observeOn(testScheduler)
            .subscribeOn(testScheduler)
            .delay(50000, TimeUnit.SECONDS)
            .subscribe()

        testViewModelSpy.getCompositeDisposables().add(testDisposable)

        testViewModelSpy.init()
        testViewModelSpy.callOnCleared()

        verify(testViewModelSpy).destroy()
        assertTrue(testDisposable.isDisposed)
    }

}