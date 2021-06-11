package com.github.jacubsz.sampleapp.persistence.model

internal class ToDoItemMockBuilder {

    companion object {
        const val TODO_ITEM_MOCK_ID_DEFAULT_VALUE = 10101
        const val TODO_ITEM_MOCK_CONTENT_DEFAULT_VALUE = "Clean the repository"
        const val TODO_ITEM_MOCK_CHECKED_DEFAULT_VALUE = true

        fun mockPersistenceToDoItem(
            id: Int? = TODO_ITEM_MOCK_ID_DEFAULT_VALUE,
            content: String? = TODO_ITEM_MOCK_CONTENT_DEFAULT_VALUE,
            checked: Boolean? = TODO_ITEM_MOCK_CHECKED_DEFAULT_VALUE
        ) = ToDoItem(id, content, checked)

        fun mockSharedToDoItem(
            id: Int? = TODO_ITEM_MOCK_ID_DEFAULT_VALUE,
            content: String = TODO_ITEM_MOCK_CONTENT_DEFAULT_VALUE,
            checked: Boolean = TODO_ITEM_MOCK_CHECKED_DEFAULT_VALUE
        ) = com.github.jacubsz.sampleapp.contract.model.ToDoItem(id, content, checked)

    }
}