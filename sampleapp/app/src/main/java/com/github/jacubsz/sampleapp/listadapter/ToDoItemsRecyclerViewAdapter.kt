package com.github.jacubsz.sampleapp.listadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.jacubsz.sampleapp.BR
import com.github.jacubsz.sampleapp.businesslogiccentre.model.ToDoItem
import com.github.jacubsz.sampleapp.databinding.ItemTodoBinding

class ToDoItemsRecyclerViewAdapter(
    private val dataList: MutableList<ToDoItem> = mutableListOf(),
    private val onItemClick: (ToDoItem) -> Unit = { _ -> },
    private val onItemSwipedOut: (ToDoItem) -> Unit = { _ -> }
) : RecyclerView.Adapter<ToDoItemsRecyclerViewAdapter.ViewHolder>() {

    private var lastDeletedItemBackup: ToDoItem? = null

    fun update(newItems: List<ToDoItem>) {
        val diffUtil = ToDoItemsDiffUtil(dataList, newItems)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        dataList.clear()
        dataList.addAll(newItems)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutInflater.from(parent.context).run {
            ItemTodoBinding.inflate(this, parent, false)
        }
        return ViewHolder(binding) { item -> onItemClick.invoke(item) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(dataList[position])

    override fun getItemCount() = dataList.size

    fun deleteItemWithBackup(position: Int) {
        val itemBackup = dataList[position]
        dataList.removeAt(position)
        notifyItemRemoved(position)
        lastDeletedItemBackup = itemBackup
        onItemSwipedOut(itemBackup)
    }

    fun getLastDeletedItem(): ToDoItem? = lastDeletedItemBackup

    class ViewHolder(
        private val binding: ItemTodoBinding,
        private val onItemClick: (ToDoItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        val checked = ObservableBoolean()
        val content = ObservableField<String>()

        fun bind(item: ToDoItem) {
            setBindingItem()
            setObservableFieldsFromItem(item)
            initItemClick(item)
        }

        private fun setObservableFieldsFromItem(item: ToDoItem) {
            checked.set(item.checked)
            content.set(item.content)
        }

        private fun initItemClick(item: ToDoItem) {
            binding.root.setOnClickListener {
                binding.checkbox.toggle()
                callOnItemClick(item)
            }
            binding.checkbox.setOnClickListener {
                callOnItemClick(item)
            }
        }

        private fun callOnItemClick(item: ToDoItem) {
            val updatedItem = ToDoItem(
                item.id,
                item.content,
                checked.get()
            )
            onItemClick(updatedItem)
        }

        private fun setBindingItem() {
            binding.setVariable(BR.item, this)
        }
    }
}