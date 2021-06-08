package com.github.jacubsz.sampleapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.jacubsz.sampleapp.R
import com.github.jacubsz.sampleapp.databinding.ActivityAboutBinding
import com.github.jacubsz.sampleapp.viewmodel.EmptyViewModel

class AboutActivity : AppActivity<ActivityAboutBinding, EmptyViewModel>(
    R.layout.activity_about,
    EmptyViewModel::class
) {

    companion object {

        fun newIntent(context: Context) = Intent(context, AboutActivity::class.java)

    }

    override fun initView(savedInstanceState: Bundle?) {}

}