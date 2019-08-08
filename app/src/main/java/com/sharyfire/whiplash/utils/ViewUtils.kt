package com.sharyfire.whiplash.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog

fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun ViewGroup.inflaterAdapterView(@LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(this.context).inflate(layoutRes, this, false)
}

fun showInfoDialog(messageResId: Int, context: Context, neutralButtonCode: () -> Unit = {}) {
    AlertDialog.Builder(context)
        .setMessage(messageResId)
        .setNeutralButton(android.R.string.ok) { _, _ ->
            neutralButtonCode()
        }.show()
}

