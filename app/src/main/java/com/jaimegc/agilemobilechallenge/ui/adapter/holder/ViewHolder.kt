package com.jaimegc.agilemobilechallenge.ui.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class BetterViewHolder<in T>(
    view: View
): RecyclerView.ViewHolder(view) {
    abstract operator fun invoke(item: T)
}

interface BindHolder {
    fun holder(type: Int, view: View): BetterViewHolder<*>
}