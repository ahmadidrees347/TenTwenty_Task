package com.ten.twenty.task.extension

import android.content.Context
import android.content.Intent
import android.view.View

inline fun <reified T> Context.openActivity(extras: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    extras(intent)
    startActivity(intent)
}
fun View.beVisible() {
    visibility = View.VISIBLE
}

fun View.beGone() {
    visibility = View.GONE
}