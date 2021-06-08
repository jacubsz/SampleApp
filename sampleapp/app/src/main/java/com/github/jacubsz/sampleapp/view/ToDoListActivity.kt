package com.github.jacubsz.sampleapp.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.github.jacubsz.sampleapp.R
import com.github.jacubsz.sampleapp.databinding.ActivityTodoListBinding
import com.github.jacubsz.sampleapp.viewmodel.EmptyViewModel

class ToDoListActivity : AppActivity<ActivityTodoListBinding, EmptyViewModel>(
    R.layout.activity_todo_list,
    EmptyViewModel::class
) {

    private val startAddNewItemActivityForResult = registerForActivityResult(StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            //TODO: refresh the view
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(viewBinding.toolbar)
        viewBinding.toolbarLayout.title = title
        viewBinding.fab.setOnClickListener {
            startAddNewItemActivityForResult.launch(AddNewToDoItemActivity.newIntent(this))
        }
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