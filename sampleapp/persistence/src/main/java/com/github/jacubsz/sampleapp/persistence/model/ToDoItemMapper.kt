package com.github.jacubsz.sampleapp.persistence.model

/**
 * It is kind of an artificial layer, to provide solutions for such mapping situations
 */
internal fun ToDoItem.toToDoItem() = com.github.jacubsz.sampleapp.businesslogiccentre.model.ToDoItem(
    id,
    content ?: "",
    checked ?: false
)

internal fun com.github.jacubsz.sampleapp.businesslogiccentre.model.ToDoItem.toToDoItem() = ToDoItem(
    id,
    content,
    checked
)