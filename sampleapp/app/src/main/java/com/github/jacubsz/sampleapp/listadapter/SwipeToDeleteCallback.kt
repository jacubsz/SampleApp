package com.github.jacubsz.sampleapp.listadapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteCallback(
    private val adapter: ToDoItemsRecyclerViewAdapter
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.END) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.deleteItemWithBackup(viewHolder.adapterPosition)
    }
}