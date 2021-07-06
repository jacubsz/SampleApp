package com.github.jacubsz.sampleapp.viewactions

import android.content.Context
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import com.github.jacubsz.sampleapp.R
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import java.util.Locale


class ToDoListViewActions(
    private val context: Context,
    private val uiTestActions: BasicUiTestActions
) {

    private val addNewToDoItemButton = uiTestActions.getResourceName(R.id.fab)

    fun showAddNewItemView() {
        val fabButton = uiTestActions.waitForUiObject(addNewToDoItemButton)
        assertNotNull(fabButton)
        fabButton.click()
    }

    fun scrollItemsListForContent(content: String) {
        val appView = UiScrollable(UiSelector().scrollable(true))
        appView.scrollIntoView(UiSelector().text(content))
        uiTestActions.findUiObjectWithText(content)
    }

    fun deleteItemWithContent(content: String) {
        val item = uiTestActions.findUiObjectWithText(content)
        assertNotNull(item)
        item.swipe(Direction.RIGHT, 0.9f)
    }

    fun checkIfItemWithContentDoesNotExist(content: String) {
        val item = uiTestActions.checkIfUiObjectWithTextExists(content)
        assertNull(item)
    }

    fun clickUndoAction() {
        val item = uiTestActions.findUiObjectWithText(
            context.getString(R.string.deleted_item_undo_button_label)
                .uppercase(Locale.getDefault())
        )
        item.click()
    }

}