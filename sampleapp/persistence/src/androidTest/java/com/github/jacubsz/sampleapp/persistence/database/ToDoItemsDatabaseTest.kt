package com.github.jacubsz.sampleapp.persistence.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.jacubsz.sampleapp.persistence.model.ToDoItem
import com.github.jacubsz.sampleapp.persistence.model.ToDoItemDao
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ToDoItemsDatabaseTest {

    companion object {
        private const val TODO_ITEM_1_CONTENT = "Buy potatoes"
        private const val TODO_ITEM_1_CHECKED = false
        private const val TODO_ITEM_2_CONTENT = "Buy sewer pipes"
        private const val TODO_ITEM_2_CHECKED = true
        private const val TODO_ITEM_3_CONTENT = "Buy propane"
        private const val TODO_ITEM_3_CHECKED = true
        private const val TODO_ITEM_4_CONTENT = "Assemble potato gun"
        private const val TODO_ITEM_4_CHECKED = false
    }

    private lateinit var toDoItemDao: ToDoItemDao
    private lateinit var database: ToDoItemsDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room
            .inMemoryDatabaseBuilder(
                context, ToDoItemsDatabase::class.java
            )
            .build()
        toDoItemDao = database.toDoItemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun savingNewToDoItemWithoutIdEndsWithIdSet() {
        val item = ToDoItem(null, TODO_ITEM_1_CONTENT, TODO_ITEM_1_CHECKED)

        toDoItemDao.insertAll(item).blockingAwait()
        val storedItemsResult = toDoItemDao.getAll().blockingFirst()

        assertEquals(1, storedItemsResult.size)
        assertNotNull(storedItemsResult[0].id)
    }

    @Test
    @Throws(Exception::class)
    fun savingFewNewToDoItemsWithoutIdsEndsWithSubsequentIdsSet() {
        val item1 = ToDoItem(null, TODO_ITEM_1_CONTENT, TODO_ITEM_1_CHECKED)
        val item2 = ToDoItem(null, TODO_ITEM_2_CONTENT, TODO_ITEM_2_CHECKED)
        val item3 = ToDoItem(null, TODO_ITEM_3_CONTENT, TODO_ITEM_3_CHECKED)

        toDoItemDao.insertAll(item1, item2, item3).blockingAwait()
        val storedItemsResult = toDoItemDao.getAll().blockingFirst()

        val savedItem1 = storedItemsResult[0]
        val savedItem2 = storedItemsResult[1]
        val savedItem3 = storedItemsResult[2]

        assertEquals(3, storedItemsResult.size)
        assertNotNull(savedItem1.id)
        assertNotNull(savedItem2.id)
        assertNotNull(savedItem3.id)
        assertEquals(1, savedItem2.id?.minus(savedItem1.id ?: 0))
        assertEquals(1, savedItem3.id?.minus(savedItem2.id ?: 0))
    }


    @Test
    @Throws(Exception::class)
    fun savingNewToDoItem() {
        val item = ToDoItem(1, TODO_ITEM_1_CONTENT, TODO_ITEM_1_CHECKED)

        toDoItemDao.insertAll(item).blockingAwait()
        val storedItemsResult = toDoItemDao.getAll().blockingFirst()

        assertEquals(1, storedItemsResult.size)
        assertEquals(item, storedItemsResult[0])
    }

    @Test
    @Throws(Exception::class)
    fun savingMultipleNewToDoItemsAtTheSameTime() {
        val item1 = ToDoItem(1, TODO_ITEM_1_CONTENT, TODO_ITEM_1_CHECKED)
        val item2 = ToDoItem(2, TODO_ITEM_2_CONTENT, TODO_ITEM_2_CHECKED)
        val item3 = ToDoItem(3, TODO_ITEM_3_CONTENT, TODO_ITEM_3_CHECKED)

        toDoItemDao.insertAll(item1, item2, item3).blockingAwait()
        val storedItemsResult = toDoItemDao.getAll().blockingFirst()

        assertEquals(3, storedItemsResult.size)
        assertEquals(item1, storedItemsResult[0])
        assertEquals(item2, storedItemsResult[1])
        assertEquals(item3, storedItemsResult[2])
    }

    @Test
    @Throws(Exception::class)
    fun updatingToDoItem() {
        val item = ToDoItem(1, TODO_ITEM_2_CONTENT, TODO_ITEM_2_CHECKED)

        toDoItemDao.insertAll(item).blockingAwait()
        val testFlowable = toDoItemDao.getAll()

        var storedItemsResult = testFlowable.blockingFirst()

        assertEquals(1, storedItemsResult.size)
        assertEquals(item, storedItemsResult[0])

        val itemUpdated = item.copy(checked = item.checked?.not())
        toDoItemDao.update(itemUpdated).blockingAwait()

        storedItemsResult = testFlowable.blockingFirst()

        assertEquals(1, storedItemsResult.size)
        assertEquals(itemUpdated, storedItemsResult[0])
    }

    @Test
    @Throws(Exception::class)
    fun updatingToDoItemWithDifferentTestingApproach() {
        val item = ToDoItem(1, TODO_ITEM_1_CONTENT, TODO_ITEM_1_CHECKED)

        toDoItemDao.insertAll(item).blockingAwait()
        val testSubscriber = toDoItemDao.getAll().test()

        testSubscriber.awaitCount(1)

        val itemUpdated = item.copy(checked = item.checked?.not())
        toDoItemDao.update(itemUpdated).blockingAwait()

        testSubscriber.awaitCount(2)
        testSubscriber.assertValues(listOf(item), listOf(itemUpdated))
    }

    @Test
    @Throws(Exception::class)
    fun deletingToDoItem() {
        val item = ToDoItem(1, TODO_ITEM_4_CONTENT, TODO_ITEM_4_CHECKED)

        toDoItemDao.insertAll(item).blockingAwait()
        val testSubscriber = toDoItemDao.getAll().test()

        testSubscriber.awaitCount(1)

        toDoItemDao.delete(item).blockingAwait()

        testSubscriber.awaitCount(2)
        testSubscriber.assertValues(listOf(item), emptyList())
    }

    @Test
    @Throws(Exception::class)
    fun deletingOneToDoItemOutOfThree() {
        val item1 = ToDoItem(1, TODO_ITEM_2_CONTENT, TODO_ITEM_2_CHECKED)
        val item2 = ToDoItem(5, TODO_ITEM_3_CONTENT, TODO_ITEM_3_CHECKED)
        val item3 = ToDoItem(9, TODO_ITEM_4_CONTENT, TODO_ITEM_4_CHECKED)

        toDoItemDao.insertAll(item1, item2, item3).blockingAwait()
        val testSubscriber = toDoItemDao.getAll().test()

        testSubscriber.awaitCount(1)

        toDoItemDao.delete(item2).blockingAwait()

        testSubscriber.awaitCount(2)
        testSubscriber.assertValues(listOf(item1, item2, item3), listOf(item1, item3))
    }

    @Test
    @Throws(Exception::class)
    fun deletionOfOneItemOutOfThree() {
        val item1 = ToDoItem(1, TODO_ITEM_2_CONTENT, TODO_ITEM_2_CHECKED)
        val item2 = ToDoItem(5, TODO_ITEM_3_CONTENT, TODO_ITEM_3_CHECKED)
        val item3 = ToDoItem(9, TODO_ITEM_4_CONTENT, TODO_ITEM_4_CHECKED)

        testDeletion(listOf(item1, item2, item3), listOf(item1))
    }

    @Test
    @Throws(Exception::class)
    fun deletionOfTwoItemsOutOfFour() {
        val item1 = ToDoItem(1, TODO_ITEM_1_CONTENT, TODO_ITEM_1_CHECKED)
        val item2 = ToDoItem(2, TODO_ITEM_2_CONTENT, TODO_ITEM_2_CHECKED)
        val item3 = ToDoItem(3, TODO_ITEM_3_CONTENT, TODO_ITEM_3_CHECKED)
        val item4 = ToDoItem(4, TODO_ITEM_4_CONTENT, TODO_ITEM_4_CHECKED)

        testDeletion(listOf(item1, item2, item3, item4), listOf(item1, item4))
    }

    private fun testDeletion(itemsToAdd: List<ToDoItem>, itemsToRemove: List<ToDoItem>) {
        toDoItemDao.insertAll(*itemsToAdd.toTypedArray()).blockingAwait()
        val testSubscriber = toDoItemDao.getAll().test()

        testSubscriber.awaitCount(1)

        val sublistsOfResults = mutableListOf<ToDoItem>().also { it.addAll(itemsToAdd) }
        val allFlowableResults = mutableListOf<List<ToDoItem>>().also { it.add(itemsToAdd.toList()) }

        itemsToRemove.forEach {
            toDoItemDao.delete(it).blockingAwait()
            sublistsOfResults.remove(it)
            allFlowableResults.add(sublistsOfResults.toList())

            testSubscriber.awaitCount(allFlowableResults.size + 1)
        }

        testSubscriber.assertValues(*allFlowableResults.toTypedArray())
    }
}