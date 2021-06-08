package com.github.jacubsz.sampleapp.rxutils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

enum class Thread(val scheduler: Scheduler) {
    IO(Schedulers.io()),
    MAIN(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.dispatch(subscribeOn: Thread, observeOn: Thread): Observable<T> =
    this.subscribeOn(subscribeOn.scheduler)
        .observeOn(observeOn.scheduler)

fun Completable.dispatch(subscribeOn: Thread, observeOn: Thread): Completable =
    this.subscribeOn(subscribeOn.scheduler)
        .observeOn(observeOn.scheduler)