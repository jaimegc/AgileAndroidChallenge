package com.jaimegc.agilemobilechallenge.common.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.View
import android.view.inputmethod.InputMethodManager


fun Intent.addExtra(key: String, value: Any?) {
    when (value) {
        is Long -> putExtra(key, value)
        is String -> putExtra(key, value)
        is Boolean -> putExtra(key, value)
        is Float -> putExtra(key, value)
        is Double -> putExtra(key, value)
        is Int -> putExtra(key, value)
        is Parcelable -> putExtra(key, value)
    }
}

fun Intent.addExtra(key: String, value: ArrayList<out Parcelable>?) {
    putParcelableArrayListExtra(key, value)
}

fun Activity.hideKeyboard() {
    val view: View? = findViewById(android.R.id.content)

    view?.run {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

inline fun <reified T> Activity.getExtra(extra: String): T? {
    return intent.extras?.get(extra) as? T?
}