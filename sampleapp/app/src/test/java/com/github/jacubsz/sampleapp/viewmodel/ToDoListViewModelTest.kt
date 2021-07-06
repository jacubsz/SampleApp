package com.github.jacubsz.sampleapp.viewmodel

import com.github.jacubsz.sampleapp.RxTest
import com.github.jacubsz.sampleapp.contract.datasource.ToDoItemsDataSource
import com.github.jacubsz.sampleapp.contract.model.ToDoItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class ToDoListViewModelTest : RxTest() {

    @Mock lateinit var toDoItemsDataSource: ToDoItemsDataSource

    private fun getViewModelWithMockedDataSource() = ToDoListViewModel(toDoItemsDataSource)

    @Test
    fun `subscribes to items from data source on init`() {
        val viewModel = getViewModelWithMockedDataSource()
        val testFlowable = viewModel.toDoItemsFlowable.test()
        val items = listOf(
            ToDoItem(1, "Content 1", true),
            ToDoItem(2, "Content 2", false),
            ToDoItem(3, "Content 3", false),
            ToDoItem(4, "Content 4", true)
        )

        given(toDoItemsDataSource.getItems()).willReturn(Flowable.just(items))

        viewModel.init()

        testFlowable.assertNoErrors()
        testFlowable.assertValue(items)
    }

    @Test
    fun `inserts new item with data source when insertItem method is called`() {
        val viewModel = getViewModelWithMockedDataSource()
        val testFlowable = viewModel.toDoItemsFlowable.test()
        val additionalItem = ToDoItem(4, "Content 4", true)

        given(toDoItemsDataSource.insertItems(any())).willReturn(Completable.complete())

        viewModel.insertItem(additionalItem)

        verify(toDoItemsDataSource).insertItems(listOf(additionalItem))
        testFlowable.assertNoErrors()
    }

    @Test
    fun `deletes an item with data source when deleteItem method is called`() {
        val viewModel = getViewModelWithMockedDataSource()
        val testFlowable = viewModel.toDoItemsFlowable.test()
        val itemToDelete = ToDoItem(4, "Content 4", true)

        given(toDoItemsDataSource.deleteItem(any())).willReturn(Completable.complete())

        viewModel.deleteItem(itemToDelete)

        verify(toDoItemsDataSource).deleteItem(itemToDelete)
        testFlowable.assertNoErrors()
    }

    @Test
    fun `propagates changes of an item to data source when updateItem method is called`() {
        val viewModel = getViewModelWithMockedDataSource()
        val testFlowable = viewModel.toDoItemsFlowable.test()
        val itemToUpdate = ToDoItem(4, "Content 4", true)

        given(toDoItemsDataSource.updateItem(any())).willReturn(Completable.complete())

        viewModel.updateItem(itemToUpdate)

        verify(toDoItemsDataSource).updateItem(itemToUpdate)
        testFlowable.assertNoErrors()
    }

}