package com.jaimegc.agilemobilechallenge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jaimegc.agilemobilechallenge.ui.model.viewmodel.ItemViewModel
import com.jaimegc.agilemobilechallenge.ui.adapter.holder.BetterViewHolder
import com.jaimegc.agilemobilechallenge.ui.adapter.holder.factory.ViewItemsFactoryImpl

class ViewItemsAdapter : RecyclerView.Adapter<BetterViewHolder<ItemViewModel>>() {

    private var items = mutableListOf<ItemViewModel>()
    private val typeFactory = ViewItemsFactoryImpl()

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: BetterViewHolder<ItemViewModel>, position: Int) = holder(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        parent.run {
            val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            typeFactory.holder(viewType, view) as BetterViewHolder<ItemViewModel>
        }

    override fun getItemViewType(position: Int) = items[position](typeFactory)

    override fun getItemId(position: Int) = position.toLong()

    fun addAll(collection: Collection<ItemViewModel>) {
        items.clear()
        items.addAll(collection)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}