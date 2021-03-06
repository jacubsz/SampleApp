package com.github.jacubsz.sampleapp.viewmodel

import androidx.databinding.ObservableField
import com.github.jacubsz.sampleapp.R
import com.github.jacubsz.sampleapp.contract.datasource.ToDoItemsDataSource
import com.github.jacubsz.sampleapp.contract.model.ToDoItem
import com.github.jacubsz.sampleapp.rxutils.Thread
import com.github.jacubsz.sampleapp.rxutils.dispatch
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class AddNewItemViewModel @Inject constructor(
    private val toDoItemsDataSource: ToDoItemsDataSource
) : AppViewModel() {

    val content = ObservableField<String>()
    val errorMessage = ObservableField<Int>() // ObservableField used on purpose to have an optional value

    private val newItemAddedSubject = PublishSubject.create<Unit>()
    val newItemAddedObservable: Observable<Unit> = newItemAddedSubject.hide()

    override fun init() {}

    fun onAddNewItemClick() {
        if (content.get().isNullOrBlank()) {
            errorMessage.set(R.string.add_new_item_missing_content_error)
        } else {
            errorMessage.set(null)
            content.get()?.let {
                val newItem = ToDoItem(null, it, false)
                toDoItemsDataSource
                    .insertItems(listOf(newItem))
                    .dispatch(Thread.IO, Thread.IO)
                    .subscribeBy(
                        onComplete = { newItemAddedSubject.onNext(Unit) },
                        onError = { errorMessage.set(R.string.add_new_item_generic_error) }
                    )
                    .addTo(disposables)
            }
        }
    }
}