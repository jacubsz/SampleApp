package com.github.jacubsz.sampleapp.listadapter

import com.github.jacubsz.sampleapp.contract.model.ToDoItem
import io.mockk.confirmVerified
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ToDoItemsRecyclerViewAdapterTest {

    @Test
    fun dataListCountIsPropagatedAsTheItemCountOfTheAdapter() {
        val items = mutableListOf(
            ToDoItem(1, "Content 1", true),
            ToDoItem(2, "Content 2", false),
            ToDoItem(3, "Content 3", false),
            ToDoItem(4, "Content 4", true),
        )
        val toDoItemsRecyclerViewAdapter = ToDoItemsRecyclerViewAdapter(items)
        assertEquals(items.size, toDoItemsRecyclerViewAdapter.itemCount)
    }

    @Test
    fun updatingAdapterResultsInANewItemCountOfTheAdapter() {
        val items = listOf(
            ToDoItem(1, "Content 1", false),
            ToDoItem(2, "Content 2", true),
            ToDoItem(3, "Content 3", false),
        )

        val toDoItemsRecyclerViewAdapter = spyk(ToDoItemsRecyclerViewAdapter())

        assertEquals(0, toDoItemsRecyclerViewAdapter.itemCount)
        toDoItemsRecyclerViewAdapter.update(items)
        assertEquals(items.size, toDoItemsRecyclerViewAdapter.itemCount)
        verify {
            toDoItemsRecyclerViewAdapter.itemCount
            toDoItemsRecyclerViewAdapter.update(items)
            toDoItemsRecyclerViewAdapter.notifyItemRangeInserted(0, items.size)
            toDoItemsRecyclerViewAdapter.itemCount
        }
        confirmVerified(toDoItemsRecyclerViewAdapter)
    }

    @Test
    fun deletingItemWithBackupResultsInItemBeingStoredAsBackedUpOne() {
        val items = listOf(
            ToDoItem(1, "Content 1", false),
            ToDoItem(2, "Content 2", true),
            ToDoItem(3, "Content 3", false),
        )
        val itemToBeRemovedIndex = 1

        val toDoItemsRecyclerViewAdapter = spyk(ToDoItemsRecyclerViewAdapter(items.toMutableList()))

        assertEquals(items.size, toDoItemsRecyclerViewAdapter.itemCount)
        assertNull(toDoItemsRecyclerViewAdapter.lastDeletedItem)
        toDoItemsRecyclerViewAdapter.deleteItemWithBackup(itemToBeRemovedIndex)
        assertEquals(items[itemToBeRemovedIndex], toDoItemsRecyclerViewAdapter.lastDeletedItem)
        assertEquals(items.size - 1, toDoItemsRecyclerViewAdapter.itemCount)
        verify {
            toDoItemsRecyclerViewAdapter.itemCount
            toDoItemsRecyclerViewAdapter.lastDeletedItem
            toDoItemsRecyclerViewAdapter.deleteItemWithBackup(itemToBeRemovedIndex)
            toDoItemsRecyclerViewAdapter.notifyItemRemoved(itemToBeRemovedIndex)
            toDoItemsRecyclerViewAdapter.lastDeletedItem
            toDoItemsRecyclerViewAdapter.itemCount
        }
        confirmVerified(toDoItemsRecyclerViewAdapter)
    }
}