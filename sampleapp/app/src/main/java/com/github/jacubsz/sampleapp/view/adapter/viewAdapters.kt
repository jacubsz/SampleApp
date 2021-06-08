package com.github.jacubsz.sampleapp.view.adapter

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun TextInputLayout.setErrorText(errorMessageResId: Int?) {
    error = errorMessageResId?.let { context.getString(it) } ?: ""
}