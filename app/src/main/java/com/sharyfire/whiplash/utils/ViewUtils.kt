package com.sharyfire.whiplash.utils

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog

fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun showInfoDialog(messageResId: Int, context: Context, neutralButtonCode: () -> Unit = {}) {
    AlertDialog.Builder(context)
        .setMessage(messageResId)
        .setNeutralButton(android.R.string.ok) { _, _ ->
            neutralButtonCode()
        }.show()
}