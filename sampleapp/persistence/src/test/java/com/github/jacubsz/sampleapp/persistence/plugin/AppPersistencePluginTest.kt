package com.github.jacubsz.sampleapp.persistence.plugin

import com.github.jacubsz.sampleapp.persistence.model.ToDoItemDao
import com.github.jacubsz.sampleapp.persistence.model.ToDoItemMockBuilder
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.anyVararg

@RunWith(MockitoJUnitRunner::class)
internal class AppPersistencePluginTest {

    @Mock lateinit var toDoItemDao: ToDoItemDao

    private lateinit var appPersistencePlugin: AppPersistencePlugin

    private val toDoItemPersistenceMock = ToDoItemMockBuilder.mockPersistenceToDoItem()
    private val toDoItemSharedMock = ToDoItemMockBuilder.mockSharedToDoItem()

    @Before
    fun setUp() {
        appPersistencePlugin = AppPersistencePlugin(toDoItemDao)
    }

    @Test
    fun mappingGetItemsFromPersistenceToSharedModelInReturnedObject() {
        given(toDoItemDao.getAll())
            .willReturn(Flowable.just(listOf(toDoItemPersistenceMock)))

        val testObservable = appPersistencePlugin.getItems().test()
        testObservable.awaitCount(1)
        testObservable.assertValues(listOf(toDoItemSharedMock))
    }

    @Test
    fun mappingUpdatedItemFromSharedToPersistenceModelInParameters() {
        given(toDoItemDao.update(toDoItemPersistenceMock)).willReturn(Completable.complete())

        val testObservable = appPersistencePlugin.updateItem(toDoItemSharedMock).test()
        testObservable.awaitCount(1)
        testObservable.assertComplete()
    }

    @Test
    fun mappingInsertAllItemsFromSharedToPersistenceModelInParameters() {
        val additionalId = 70010
        val additionalContent = "Unknown mission"
        val toDoItemSharedMock2 = ToDoItemMockBuilder.mockSharedToDoItem(id = additionalId, content = additionalContent)
        val toDoItemPersistenceMock2 = ToDoItemMockBuilder.mockPersistenceToDoItem(id = additionalId, content = additionalContent)
        val listOfToDoItemsSharedMocks = listOf(toDoItemSharedMock, toDoItemSharedMock2)
        val listOfToDoItemsPersistenceMocks = listOf(toDoItemPersistenceMock, toDoItemPersistenceMock2)

        given(toDoItemDao.insertAll(anyVararg())).willReturn(Completable.complete())

        val testObservable = appPersistencePlugin.insertItems(listOfToDoItemsSharedMocks).test()
        testObservable.awaitCount(1)
        testObservable.assertComplete()
        verify(toDoItemDao).insertAll(*listOfToDoItemsPersistenceMocks.toTypedArray())
    }

    @Test
    fun mappingDeletedItemFromSharedToPersistenceModelInParameters() {
        given(toDoItemDao.delete(any())).willReturn(Completable.complete())

        val testObservable = appPersistencePlugin.deleteItem(toDoItemSharedMock).test()
        testObservable.awaitCount(1)
        testObservable.assertComplete()
        verify(toDoItemDao).delete(toDoItemPersistenceMock)
    }
}