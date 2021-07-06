package com.github.jacubsz.sampleapp.viewactions

import android.content.Context
import com.github.jacubsz.sampleapp.R
import org.junit.Assert.assertNotNull

class AddNewToDoItemViewActions(
    private val context: Context,
    private val uiTestActions: BasicUiTestActions
) {

    private val newItemContentInputView = uiTestActions.getResourceName(R.id.edittext_new_item_content)
    private val addNewItemButton = uiTestActions.getResourceName(R.id.button_add_new_item)

    fun fillInNewItemContent(content: String) {
        val inputView = uiTestActions.waitForUiObject(newItemContentInputView)
        assertNotNull(inputView)
        inputView.text = content
    }

    fun clickAddNewButton() {
        val button = uiTestActions.waitForUiObject(addNewItemButton)
        assertNotNull(button)
        button.click()
    }

    fun checkErrorMessage() {
        val errorMessageUiObject = uiTestActions.findUiObjectWithText(
            context.getString(R.string.add_new_item_missing_content_error)
        )
        assertNotNull(errorMessageUiObject)
    }
}