package com.github.jacubsz.sampleapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.jacubsz.sampleapp.R
import com.github.jacubsz.sampleapp.databinding.ActivityAddNewTodoItemBinding
import com.github.jacubsz.sampleapp.rxutils.Thread
import com.github.jacubsz.sampleapp.rxutils.dispatch
import com.github.jacubsz.sampleapp.viewmodel.AddNewItemViewModel
import io.reactivex.rxjava3.kotlin.addTo

class AddNewToDoItemActivity : AppActivity<ActivityAddNewTodoItemBinding, AddNewItemViewModel>(
    R.layout.activity_add_new_todo_item,
    AddNewItemViewModel::class
) {

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, AddNewToDoItemActivity::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {
        closeOnNewItemAdded()
    }

    private fun closeOnNewItemAdded() {
        viewModel.newItemAddedObservable
            .dispatch(Thread.IO, Thread.MAIN)
            .subscribe { setOkResultAndClose() }
            .addTo(disposables)
    }

    private fun setOkResultAndClose() {
        setResult(RESULT_OK)
        finish()
    }
}