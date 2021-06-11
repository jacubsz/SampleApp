package com.github.jacubsz.sampleapp.contract.datasource

import com.github.jacubsz.sampleapp.contract.model.ToDoItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface ToDoItemsDataSource {

    fun getItems(): Flowable<List<ToDoItem>>

    fun updateItem(item: ToDoItem): Completable

    fun insertItems(items: List<ToDoItem>): Completable

    fun deleteItem(item: ToDoItem): Completable

}