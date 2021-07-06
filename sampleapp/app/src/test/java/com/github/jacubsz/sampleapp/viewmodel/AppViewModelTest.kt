package com.github.jacubsz.sampleapp.viewmodel

import com.github.jacubsz.sampleapp.RxTest
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify
import java.util.concurrent.TimeUnit

class AppViewModelTest : RxTest() {

    private val testViewModel = object : AppViewModel() {

        override fun init() {}

        fun getCompositeDisposables() = disposables

        fun callOnCleared() = onCleared()

    }

    @Test
    fun `onCleared clears observables and disposes them`() {
        val testViewModelSpy = Mockito.spy(testViewModel)
        val testDisposable = Observable.just(Unit).delay(50000, TimeUnit.SECONDS).subscribe()
        testViewModelSpy.getCompositeDisposables().add(testDisposable)

        testViewModelSpy.init()
        testViewModelSpy.callOnCleared()

        verify(testViewModelSpy).destroy()
        assertTrue(testDisposable.isDisposed)
    }

}