package com.github.jacubsz.sampleapp.listadapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class SwipeToDeleteCallbackTest {

    @Mock lateinit var adapter: ToDoItemsRecyclerViewAdapter
    @Mock lateinit var viewHolder: RecyclerView.ViewHolder

    @Test
    fun `check if onSwipe is called on swipe to end`() {
        testItemDeletionOnSwipeWithDirection(ItemTouchHelper.END, true)
    }

    @Test
    fun `verify if onSwipe is not called on swipe to start`() {
        testItemDeletionOnSwipeWithDirection(ItemTouchHelper.START, false)
    }

    @Test
    fun `verify if onSwipe is not called on swipe up`() {
        testItemDeletionOnSwipeWithDirection(ItemTouchHelper.UP, false)
    }

    @Test
    fun `verify if onSwipe is not called on swipe down`() {
        testItemDeletionOnSwipeWithDirection(ItemTouchHelper.DOWN, false)
    }

    @Test
    fun `verify if onSwipe is not called on swipe right`() {
        testItemDeletionOnSwipeWithDirection(ItemTouchHelper.RIGHT, false)
    }

    @Test
    fun `verify if onSwipe is not called on swipe left`() {
        testItemDeletionOnSwipeWithDirection(ItemTouchHelper.LEFT, false)
    }

    private fun testItemDeletionOnSwipeWithDirection(direction: Int, isDeletionExpectedToBeCalled: Boolean) {
        val swipeToDeleteCallback = SwipeToDeleteCallback(adapter)
        swipeToDeleteCallback.onSwiped(viewHolder, direction)
        if (isDeletionExpectedToBeCalled) {
            verify(adapter).deleteItemWithBackup(any())
        } else {
            verify(adapter, never()).deleteItemWithBackup(any())
        }
    }
}