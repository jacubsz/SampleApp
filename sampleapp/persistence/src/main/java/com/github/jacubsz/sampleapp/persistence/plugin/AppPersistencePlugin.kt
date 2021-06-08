package com.github.jacubsz.sampleapp.persistence.plugin

import com.github.jacubsz.sampleapp.businesslogiccentre.datasource.ToDoItemsDataSource
import com.github.jacubsz.sampleapp.businesslogiccentre.model.ToDoItem
import com.github.jacubsz.sampleapp.persistence.model.ToDoItemDao
import com.github.jacubsz.sampleapp.persistence.model.toToDoItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class AppPersistencePlugin @Inject internal constructor(
    private val toDoItemsDao: ToDoItemDao
) : ToDoItemsDataSource {

    override fun getItems(): Flowable<List<ToDoItem>> =
        toDoItemsDao.getAll()
            .map { items -> items.map { it.toToDoItem() } }

    override fun updateItem(item: ToDoItem): Completable =
        toDoItemsDao.update(item.toToDoItem())

    override fun insertItems(items: List<ToDoItem>): Completable =
        toDoItemsDao.insertAll(*items.map { it.toToDoItem() }.toTypedArray())

    override fun deleteItem(item: ToDoItem): Completable =
        toDoItemsDao.delete(item.toToDoItem())

}