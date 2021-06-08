package com.github.jacubsz.sampleapp.viewmodel

import androidx.databinding.ObservableField
import com.github.jacubsz.sampleapp.businesslogiccentre.datasource.ToDoItemsDataSource
import com.github.jacubsz.sampleapp.businesslogiccentre.model.ToDoItem
import com.github.jacubsz.sampleapp.rxutils.Thread
import com.github.jacubsz.sampleapp.rxutils.dispatch
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class AddNewItemViewModel @Inject constructor(
    private val toDoItemsDataSource: ToDoItemsDataSource
) : AppViewModel() {

    val content = ObservableField<String>()

    private val newItemAddedSubject = PublishSubject.create<Unit>()
    val newItemAddedObservable = newItemAddedSubject.hide()

    override fun init() {}

    fun onAddNewItemClick() {
        val newItem = ToDoItem(0, content.get() ?: "", false)
        toDoItemsDataSource
            .insertItems(listOf(newItem))
            .dispatch(Thread.IO, Thread.IO)
            .subscribe { newItemAddedSubject.onNext(Unit) }
            .addTo(disposables)
    }
}