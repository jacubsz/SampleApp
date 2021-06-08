package com.github.jacubsz.sampleapp.listadapter

import android.view.LayoutInflater
import android.view.ViewGroup
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

        fun bind(item: ToDoItem) {
            binding.root.setOnClickListener {
                binding.checkbox.toggle()
                onItemClick(item)
            }
            binding.setVariable(BR.item, item)
        }
    }
}