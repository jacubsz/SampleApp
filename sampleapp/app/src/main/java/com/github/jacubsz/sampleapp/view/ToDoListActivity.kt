package com.github.jacubsz.sampleapp.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.jacubsz.sampleapp.R
import com.github.jacubsz.sampleapp.databinding.ActivityTodoListBinding
import com.github.jacubsz.sampleapp.listadapter.ToDoItemsRecyclerViewAdapter
import com.github.jacubsz.sampleapp.rxutils.Thread
import com.github.jacubsz.sampleapp.rxutils.dispatch
import com.github.jacubsz.sampleapp.viewmodel.ToDoListViewModel
import io.reactivex.rxjava3.kotlin.addTo

class ToDoListActivity : AppActivity<ActivityTodoListBinding, ToDoListViewModel>(
    R.layout.activity_todo_list,
    ToDoListViewModel::class
) {

    private val startAddNewItemActivityForResult = registerForActivityResult(StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            //TODO: refresh the view
        }
    }

    // TODO: add on click action to save new state to data source
    private val toDoItemsRecyclerViewAdapter = ToDoItemsRecyclerViewAdapter()

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}