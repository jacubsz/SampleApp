package com.github.jacubsz.sampleapp.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.github.jacubsz.sampleapp.viewmodel.EmptyViewModel
import com.github.jacubsz.sampleapp.R
import com.github.jacubsz.sampleapp.databinding.ActivityScrollingBinding
import com.google.android.material.snackbar.Snackbar

class ScrollingActivity : AppActivity<ActivityScrollingBinding, EmptyViewModel>(
    R.layout.activity_scrolling,
    EmptyViewModel::class
) {

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(viewBinding.toolbar)
        viewBinding.toolbarLayout.title = title
        viewBinding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}