package com.github.jacubsz.sampleapp.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.jacubsz.sampleapp.R
import com.github.jacubsz.sampleapp.businesslogiccentre.model.ToDoItem
import com.github.jacubsz.sampleapp.databinding.ActivityTodoListBinding
import com.github.jacubsz.sampleapp.listadapter.SwipeToDeleteCallback
import com.github.jacubsz.sampleapp.listadapter.ToDoItemsRecyclerViewAdapter
import com.github.jacubsz.sampleapp.rxutils.Thread
import com.github.jacubsz.sampleapp.rxutils.dispatch
import com.github.jacubsz.sampleapp.viewmodel.ToDoListViewModel
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.kotlin.addTo

class ToDoListActivity : AppActivity<ActivityTodoListBinding, ToDoListViewModel>(
    R.layout.activity_todo_list,
    ToDoListViewModel::class
) {

    private val startAddNewItemActivityForResult = registerForActivityResult(StartActivityForResult()) { }

    private val startAboutActivityForResult = registerForActivityResult(StartActivityForResult()) { }

    private lateinit var toDoItemsRecyclerViewAdapter: ToDoItemsRecyclerViewAdapter

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(viewBinding.toolbar)
        viewBinding.toolbarLayout.title = title
        viewBinding.fab.setOnClickListener {
            startAddNewItemActivityForResult.launch(AddNewToDoItemActivity.newIntent(this))
        }

        initRecyclerView()
        updateItemsOnChange()
    }

    private fun initRecyclerView() {
        toDoItemsRecyclerViewAdapter = ToDoItemsRecyclerViewAdapter(
            onItemClick = viewModel::updateItem,
            onItemSwipedOut = ::onRecyclerViewItemSwipedOut
        )

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(toDoItemsRecyclerViewAdapter))
        itemTouchHelper.attachToRecyclerView(viewBinding.nestedView.recyclerviewItems)

        viewBinding.nestedView.recyclerviewItems.run {
            adapter = toDoItemsRecyclerViewAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun updateItemsOnChange() {
        viewModel.toDoItemsFlowable
            .dispatch(Thread.IO, Thread.MAIN)
            .subscribe {
                toDoItemsRecyclerViewAdapter.update(it)
            }
            .addTo(disposables)
    }

    private fun launchAboutActivity(): Boolean = startAboutActivityForResult.launch(AboutActivity.newIntent(this)).let { true }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_about -> launchAboutActivity()
            else -> super.onOptionsItemSelected(item)
        }

    private fun onRecyclerViewItemSwipedOut(item: ToDoItem) {
        viewModel.deleteItem(item)
        Snackbar
            .make(
                viewBinding.root,
                R.string.deleted_item_undo_header,
                Snackbar.LENGTH_LONG
            )
            .run {
                setAction(R.string.deleted_item_undo_button_label) {
                    toDoItemsRecyclerViewAdapter.getLastDeletedItem()?.let { toDoItem ->
                        viewModel.insertItem(toDoItem)
                    }
                }
                show()
            }
    }
}