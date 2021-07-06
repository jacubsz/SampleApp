package com.github.jacubsz.sampleapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.github.jacubsz.sampleapp.viewactions.AddNewToDoItemViewActions
import com.github.jacubsz.sampleapp.viewactions.BasicUiTestActions
import com.github.jacubsz.sampleapp.viewactions.ToDoListViewActions
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AppSmokeTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val basicUiTestActions = BasicUiTestActions(context, uiDevice)

    private val newItemContent = "! Test Content !"

    private val toDoListViewActions = ToDoListViewActions(context, basicUiTestActions)
    private val addNewToDoItemViewActions = AddNewToDoItemViewActions(context, basicUiTestActions)

    @Before
    fun setUp() {
        uiDevice.pressHome()
        basicUiTestActions.openApp()
    }

    @Test
    fun smokeTest_000_showNewItemViewAndCheckErrorForEmptyContent() {
        toDoListViewActions.showAddNewItemView()
        addNewToDoItemViewActions.clickAddNewButton()
        addNewToDoItemViewActions.checkErrorMessage()
    }

    @Test
    fun smokeTest_001_addNewItem() {
        toDoListViewActions.showAddNewItemView()
        addNewToDoItemViewActions.fillInNewItemContent(newItemContent)
        addNewToDoItemViewActions.clickAddNewButton()
        toDoListViewActions.scrollItemsListForContent(newItemContent)
    }

    @Test
    fun smokeTest_002_removePreviouslyAddedItem() {
        toDoListViewActions.scrollItemsListForContent(newItemContent)
        toDoListViewActions.deleteItemWithContent(newItemContent)
        toDoListViewActions.checkIfItemWithContentDoesNotExist(newItemContent)
    }

    @Test
    fun smokeTest_003_addRemoveAndUndoRemovalOfItem() {
        toDoListViewActions.showAddNewItemView()
        addNewToDoItemViewActions.fillInNewItemContent(newItemContent)
        addNewToDoItemViewActions.clickAddNewButton()
        toDoListViewActions.scrollItemsListForContent(newItemContent)
        toDoListViewActions.deleteItemWithContent(newItemContent)
        toDoListViewActions.checkIfItemWithContentDoesNotExist(newItemContent)
        toDoListViewActions.clickUndoAction()
        toDoListViewActions.scrollItemsListForContent(newItemContent)
    }
}