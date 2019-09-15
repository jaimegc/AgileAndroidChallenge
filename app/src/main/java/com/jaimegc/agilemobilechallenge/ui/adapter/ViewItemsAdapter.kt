package com.jaimegc.agilemobilechallenge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jaimegc.agilemobilechallenge.ui.model.viewmodel.ItemViewModel
import com.jaimegc.agilemobilechallenge.ui.adapter.holder.BetterViewHolder
import com.jaimegc.agilemobilechallenge.ui.adapter.holder.factory.ViewItemsFactoryImpl
import com.jaimegc.agilemobilechallenge.ui.model.viewmodel.GitHubRepoItemOtherViewModel
import com.jaimegc.agilemobilechallenge.ui.model.viewmodel.GitHubRepoItemViewModel

class ViewItemsAdapter : ListAdapter<ItemViewModel, BetterViewHolder<ItemViewModel>>(DiffCallback) {

    private val asyncListDiffer = AsyncListDiffer(this, DiffCallback)
    private val typeFactory = ViewItemsFactoryImpl()

    override fun onBindViewHolder(holder: BetterViewHolder<ItemViewModel>, position: Int) =
        holder(asyncListDiffer.currentList[position])

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun submitList(list: List<ItemViewModel>?) = asyncListDiffer.submitList(list)

    override fun getItemViewType(position: Int) = asyncListDiffer.currentList[position](typeFactory)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetterViewHolder<ItemViewModel> =
        parent.run {
            val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            typeFactory.holder(viewType, view) as BetterViewHolder<ItemViewModel>
        }

    private object DiffCallback : DiffUtil.ItemCallback<ItemViewModel>() {
        override fun areItemsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean =
            when {
                oldItem is GitHubRepoItemViewModel && newItem is GitHubRepoItemViewModel -> oldItem.id == newItem.id
                oldItem is GitHubRepoItemOtherViewModel && newItem is GitHubRepoItemOtherViewModel -> oldItem.id == newItem.id
                else -> false
            }

        override fun areContentsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean =
            when {
                oldItem is GitHubRepoItemViewModel && newItem is GitHubRepoItemViewModel -> oldItem.id == newItem.id
                oldItem is GitHubRepoItemOtherViewModel && newItem is GitHubRepoItemOtherViewModel -> oldItem.id == newItem.id
                else -> false
            }
    }
}