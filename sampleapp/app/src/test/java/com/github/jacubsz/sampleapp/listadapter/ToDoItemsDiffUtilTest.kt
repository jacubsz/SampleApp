package com.github.jacubsz.sampleapp.listadapter

import com.github.jacubsz.sampleapp.contract.model.ToDoItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.given

@RunWith(MockitoJUnitRunner::class)
class ToDoItemsDiffUtilTest {

    @Mock lateinit var oldItems: List<ToDoItem>
    @Mock lateinit var newItems: List<ToDoItem>

    @Test
    fun `verify if ToDoItemsDiffUtil returns oldItems size`() {
        val size = 5
        given(oldItems.size).willReturn(size)
        val toDoItemsDiffUtil = ToDoItemsDiffUtil(oldItems, newItems)

        assertEquals(size, toDoItemsDiffUtil.oldListSize)
    }

    @Test
    fun `verify if ToDoItemsDiffUtil returns newItems size`() {
        val size = 5
        given(newItems.size).willReturn(size)
        val toDoItemsDiffUtil = ToDoItemsDiffUtil(oldItems, newItems)

        assertEquals(size, toDoItemsDiffUtil.newListSize)
    }

    @Test
    fun `verify if different items comparison returns false`() {
        val item1 = ToDoItem(1, "Content1", true)
        val item2 = ToDoItem(2, "Content2", false)

        given(oldItems[any()]).willReturn(item1)
        given(newItems[any()]).willReturn(item2)
        val toDoItemsDiffUtil = ToDoItemsDiffUtil(oldItems, newItems)

        assertFalse(toDoItemsDiffUtil.areItemsTheSame(1, 1))
        assertFalse(toDoItemsDiffUtil.areContentsTheSame(1, 1))
    }

    @Test
    fun `verify if same items comparison returns true`() {
        val item1 = ToDoItem(1, "Content1", true)

        given(oldItems[any()]).willReturn(item1)
        given(newItems[any()]).willReturn(item1.copy())
        val toDoItemsDiffUtil = ToDoItemsDiffUtil(oldItems, newItems)

        assertTrue(toDoItemsDiffUtil.areItemsTheSame(1, 1))
        assertTrue(toDoItemsDiffUtil.areContentsTheSame(1, 1))
    }

    @Test
    fun `verify if items with different content but same id comparison returns that items are the same but content are not`() {
        val item1 = ToDoItem(1, "Content1", true)
        val item2 = item1.copy(content = "content new")

        given(oldItems[any()]).willReturn(item1)
        given(newItems[any()]).willReturn(item2)
        val toDoItemsDiffUtil = ToDoItemsDiffUtil(oldItems, newItems)

        assertTrue(toDoItemsDiffUtil.areItemsTheSame(1, 1))
        assertFalse(toDoItemsDiffUtil.areContentsTheSame(1, 1))
    }
}