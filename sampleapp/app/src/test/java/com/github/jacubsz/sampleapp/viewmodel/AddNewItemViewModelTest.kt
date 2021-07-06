package com.github.jacubsz.sampleapp.viewmodel

import com.github.jacubsz.sampleapp.R
import com.github.jacubsz.sampleapp.RxTest
import com.github.jacubsz.sampleapp.contract.datasource.ToDoItemsDataSource
import com.github.jacubsz.sampleapp.contract.model.ToDoItem
import io.reactivex.rxjava3.core.Completable
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyZeroInteractions

@RunWith(MockitoJUnitRunner::class)
class AddNewItemViewModelTest : RxTest() {

    @Mock lateinit var toDoItemsDataSource: ToDoItemsDataSource

    private fun getViewModelWithMockedDataSource() = AddNewItemViewModel(toDoItemsDataSource)

    @Test
    fun `init does nothing`() {
        val viewModel = getViewModelWithMockedDataSource()
        val testObservable = viewModel.newItemAddedObservable.test()

        viewModel.init()

        verifyZeroInteractions(toDoItemsDataSource)
        testObservable.assertNoErrors()
        testObservable.assertNoValues()
    }

    @Test
    fun `sets error message when content is empty`() {
        val viewModel = getViewModelWithMockedDataSource()
        val testObservable = viewModel.newItemAddedObservable.test()

        viewModel.content.set("")
        viewModel.onAddNewItemClick()

        verifyZeroInteractions(toDoItemsDataSource)
        assertEquals(R.string.add_new_item_missing_content_error, viewModel.errorMessage.get())
        testObservable.assertNoErrors()
        testObservable.assertNoValues()
    }

    @Test
    fun `sets error message to null and emits observable when content is set`() {
        val viewModel = getViewModelWithMockedDataSource()
        val testObservable = viewModel.newItemAddedObservable.test()
        val testContent = "S O M E  C O N T E N T"

        given(toDoItemsDataSource.insertItems(any())).willReturn(Completable.complete())

        viewModel.content.set(testContent)
        viewModel.onAddNewItemClick()

        verify(toDoItemsDataSource).insertItems(listOf(ToDoItem(null, testContent, false)))
        assertNull(viewModel.errorMessage.get())
        testObservable.assertNoErrors()
        testObservable.assertValue(Unit)
    }

    @Test
    fun `sets error message to generic error and does not emit observable when data source throws error`() {
        val viewModel = getViewModelWithMockedDataSource()
        val testObservable = viewModel.newItemAddedObservable.test()
        val testContent = "S O M E  C O N T E N T"
        val testError = Throwable("S O M E  E R R O R")

        given(toDoItemsDataSource.insertItems(any())).willReturn(Completable.error(testError))

        viewModel.content.set(testContent)
        viewModel.onAddNewItemClick()

        verify(toDoItemsDataSource).insertItems(listOf(ToDoItem(null, testContent, false)))
        assertEquals(R.string.add_new_item_generic_error, viewModel.errorMessage.get())
        testObservable.assertNoErrors()
        testObservable.assertNoValues()
    }
}