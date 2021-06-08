package com.github.jacubsz.sampleapp.listadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.github.jacubsz.sampleapp.BR
import com.github.jacubsz.sampleapp.businesslogiccentre.model.ToDoItem
import com.github.jacubsz.sampleapp.databinding.ItemTodoBinding

class ToDoItemsRecyclerViewAdapter(
    private val dataList: MutableList<ToDoItem> = mutableListOf(),
    private val onItemClick: (ToDoItem) -> Unit = { _ -> }
) : RecyclerView.Adapter<ToDoItemsRecyclerViewAdapter.ViewHolder>() {

    fun update(newItems: List<ToDoItem>) {
        dataList.clear()
        dataList.addAll(newItems)
        notifyDataSetChanged()
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

    class ViewHolder(
        private val binding: ItemTodoBinding,
        private val onItemClick: (ToDoItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        val checked = ObservableBoolean()
        val content = ObservableField<String>()

        fun bind(item: ToDoItem) {
            setObservableFieldsFromItem(item)
            initItemClick()
            addCallbackOnCheckedChange(item)
            setBindingItem()
        }

        private fun setObservableFieldsFromItem(item: ToDoItem) {
            checked.set(item.checked)
            content.set(item.content)
        }

        private fun initItemClick() {
            binding.root.setOnClickListener {
                binding.checkbox.toggle()
            }
        }

        private fun addCallbackOnCheckedChange(item: ToDoItem) {
            checked.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    callOnItemClick(item)
                }
            })
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