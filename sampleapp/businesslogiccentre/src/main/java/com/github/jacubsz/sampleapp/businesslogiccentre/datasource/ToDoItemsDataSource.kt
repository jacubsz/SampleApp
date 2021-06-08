package com.github.jacubsz.sampleapp.businesslogiccentre.datasource

import com.github.jacubsz.sampleapp.businesslogiccentre.model.ToDoItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface ToDoItemsDataSource {

    fun getItems(): Flowable<List<ToDoItem>>

    fun updateItem(item: ToDoItem): Completable

    fun insertItems(items: List<ToDoItem>): Completable

    fun deleteItem(item: ToDoItem): Completable

}