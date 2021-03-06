package com.github.jacubsz.sampleapp.listadapter

import androidx.recyclerview.widget.DiffUtil
import com.github.jacubsz.sampleapp.contract.model.ToDoItem

class ToDoItemsDiffUtil(
    private val oldItems: List<ToDoItem>,
    private val newItems: List<ToDoItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() =
        oldItems.size

    override fun getNewListSize() =
        newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition].id == newItems[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition] == newItems[newItemPosition]

}