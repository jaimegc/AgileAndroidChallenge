package com.jaimegc.agilemobilechallenge.common.extensions

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            editable?.run {
                afterTextChanged.invoke(editable.toString())
            } ?: afterTextChanged.invoke(String())
        }
    })
}

fun EditText.searchKeyboardClicked(searchKeyboardClicked: (String) -> Unit) =
    this.setOnEditorActionListener { textView, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (textView.text.isNotEmpty()) {
                searchKeyboardClicked(textView.text.toString())
                false
            } else {
                true
            }
        } else {
            false
        }
    }

fun Context.resToText(resource: Int): String =
    this.getString(resource)