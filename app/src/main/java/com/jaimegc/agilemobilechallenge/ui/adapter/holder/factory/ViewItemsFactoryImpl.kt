package com.jaimegc.agilemobilechallenge.ui.adapter.holder.factory

import android.view.View
import com.jaimegc.agilemobilechallenge.R
import com.jaimegc.agilemobilechallenge.ui.model.GitHubRepoItem
import com.jaimegc.agilemobilechallenge.ui.adapter.holder.*

class ViewItemsFactoryImpl : ViewItemsFactory {
    companion object {
        private val gitHubRepoItemView = R.layout.view_item_github_repo
    }

    override fun type(gitHubRepoItem: GitHubRepoItem): Int =
        gitHubRepoItemView

    override fun holder(type: Int, view: View): BetterViewHolder<*> =
        when (type) {
            gitHubRepoItemView -> GitHubRepoItemHolder(view)
            else -> throw RuntimeException("Illegal view type")
        }
}