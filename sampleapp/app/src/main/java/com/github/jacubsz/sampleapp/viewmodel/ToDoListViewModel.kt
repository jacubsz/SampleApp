package com.github.jacubsz.sampleapp.viewmodel

import com.github.jacubsz.sampleapp.contract.datasource.ToDoItemsDataSource
import com.github.jacubsz.sampleapp.contract.model.ToDoItem
import com.github.jacubsz.sampleapp.rxutils.Thread
import com.github.jacubsz.sampleapp.rxutils.dispatch
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class ToDoListViewModel @Inject constructor(
    private val toDoItemsDataSource: ToDoItemsDataSource
) : AppViewModel() {

    private val toDoItems = BehaviorSubject.create<List<ToDoItem>>()
    val toDoItemsFlowable: Flowable<List<ToDoItem>> = toDoItems.toFlowable(BackpressureStrategy.LATEST)

    override fun init() {
        subscribeToItemsFromDataSource()
    }

    fun updateItem(item: ToDoItem) {
        toDoItemsDataSource.updateItem(item)
            .dispatch(Thread.IO, Thread.IO)
            .subscribe()
            .addTo(disposables)
    }

    fun deleteItem(item: ToDoItem) {
        toDoItemsDataSource.deleteItem(item)
            .dispatch(Thread.IO, Thread.IO)
            .subscribe()
            .addTo(disposables)
    }

    fun insertItem(item: ToDoItem) {
        toDoItemsDataSource.insertItems(listOf(item))
            .dispatch(Thread.IO, Thread.IO)
            .subscribe()
            .addTo(disposables)
    }

    private fun subscribeToItemsFromDataSource() {
        toDoItemsDataSource.getItems()
            .dispatch(Thread.IO, Thread.IO)
            .subscribe(toDoItems::onNext)
            .addTo(disposables)
    }
}