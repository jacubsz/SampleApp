package com.github.jacubsz.sampleapp.persistence.model

import org.junit.Assert.assertEquals
import org.junit.Test

class ToDoItemMapperKtTest {

    @Test
    fun `mapping an item from persistence to shared model`() {
        testPersistenceToSharedModelMapping(
            persistenceItemId = ToDoItemMockBuilder.TODO_ITEM_MOCK_ID_DEFAULT_VALUE,
            persistenceItemContent = ToDoItemMockBuilder.TODO_ITEM_MOCK_CONTENT_DEFAULT_VALUE,
            persistenceItemChecked = ToDoItemMockBuilder.TODO_ITEM_MOCK_CHECKED_DEFAULT_VALUE,
            sharedModelItemId = ToDoItemMockBuilder.TODO_ITEM_MOCK_ID_DEFAULT_VALUE,
            sharedModelItemContent = ToDoItemMockBuilder.TODO_ITEM_MOCK_CONTENT_DEFAULT_VALUE,
            sharedModelItemChecked = ToDoItemMockBuilder.TODO_ITEM_MOCK_CHECKED_DEFAULT_VALUE
        )
    }

    @Test
    fun `mapping an item with empty content from persistence to shared model`() {
        testPersistenceToSharedModelMapping(
            persistenceItemId = ToDoItemMockBuilder.TODO_ITEM_MOCK_ID_DEFAULT_VALUE,
            persistenceItemContent = null,
            persistenceItemChecked = ToDoItemMockBuilder.TODO_ITEM_MOCK_CHECKED_DEFAULT_VALUE,
            sharedModelItemId = ToDoItemMockBuilder.TODO_ITEM_MOCK_ID_DEFAULT_VALUE,
            sharedModelItemContent = "",
            sharedModelItemChecked = ToDoItemMockBuilder.TODO_ITEM_MOCK_CHECKED_DEFAULT_VALUE
        )
    }

    @Test
    fun `mapping an item without checked status from persistence to shared model`() {
        testPersistenceToSharedModelMapping(
            persistenceItemId = ToDoItemMockBuilder.TODO_ITEM_MOCK_ID_DEFAULT_VALUE,
            persistenceItemContent = ToDoItemMockBuilder.TODO_ITEM_MOCK_CONTENT_DEFAULT_VALUE,
            persistenceItemChecked = null,
            sharedModelItemId = ToDoItemMockBuilder.TODO_ITEM_MOCK_ID_DEFAULT_VALUE,
            sharedModelItemContent = ToDoItemMockBuilder.TODO_ITEM_MOCK_CONTENT_DEFAULT_VALUE,
            sharedModelItemChecked = false
        )
    }

    @Test
    fun `mapping an item from shared to persistence model`() {
        val persistenceToDoItem = ToDoItemMockBuilder.mockPersistenceToDoItem()
        val sharedToDoItem = ToDoItemMockBuilder.mockSharedToDoItem()

        assertEquals(persistenceToDoItem, sharedToDoItem.toToDoItem())
    }

    @Suppress("SameParameterValue")
    private fun testPersistenceToSharedModelMapping(
        persistenceItemId: Int?,
        persistenceItemContent: String?,
        persistenceItemChecked: Boolean?,
        sharedModelItemId: Int?,
        sharedModelItemContent: String,
        sharedModelItemChecked: Boolean
    ) {
        val persistenceToDoItem = ToDoItem(
            persistenceItemId,
            persistenceItemContent,
            persistenceItemChecked
        )

        val sharedToDoItem = com.github.jacubsz.sampleapp.contract.model.ToDoItem(
            sharedModelItemId,
            sharedModelItemContent,
            sharedModelItemChecked
        )

        assertEquals(sharedToDoItem, persistenceToDoItem.toToDoItem())
    }
}