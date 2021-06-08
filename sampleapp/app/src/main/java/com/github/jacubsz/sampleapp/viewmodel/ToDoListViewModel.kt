package com.github.jacubsz.sampleapp.viewmodel

import com.github.jacubsz.sampleapp.businesslogiccentre.datasource.ToDoItemsDataSource
import com.github.jacubsz.sampleapp.businesslogiccentre.model.ToDoItem
import com.github.jacubsz.sampleapp.rxutils.Thread
import com.github.jacubsz.sampleapp.rxutils.dispatch
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class ToDoListViewModel @Inject constructor(
    private val toDoItemsDataSource: ToDoItemsDataSource
) : AppViewModel() {

    private val toDoItems = BehaviorSubject.create<List<ToDoItem>>()
    val toDoItemsFlowable: Flowable<List<ToDoItem>> = toDoItems.toFlowable(BackpressureStrategy.LATEST)

    private val reloadSignal = PublishSubject.create<Unit>()

    override fun init() {
        loadDataOnStartAndReload()
    }

    fun refreshList() {
        reloadSignal.onNext(Unit)
    }

    fun updateItem(item: ToDoItem) {
        toDoItemsDataSource.updateItem(item)
            .dispatch(Thread.IO, Thread.IO)
            .subscribe()
            .addTo(disposables)
    }

    private fun loadDataOnStartAndReload() {
        reloadSignal.startWithItem(Unit)
            .toFlowable(BackpressureStrategy.LATEST)
            .dispatch(Thread.IO, Thread.IO)
            .flatMap { toDoItemsDataSource.getItems() }
            .subscribe(toDoItems::onNext)
            .addTo(disposables)
    }
}