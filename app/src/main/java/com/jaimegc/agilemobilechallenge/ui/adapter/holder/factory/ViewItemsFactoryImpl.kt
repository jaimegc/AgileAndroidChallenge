package com.jaimegc.agilemobilechallenge.ui.adapter.holder.factory

import android.view.View
import com.jaimegc.agilemobilechallenge.R
import com.jaimegc.agilemobilechallenge.ui.model.GitHubRepoItem
import com.jaimegc.agilemobilechallenge.ui.adapter.holder.*
import com.jaimegc.agilemobilechallenge.ui.model.GitHubRepoItemOther

class ViewItemsFactoryImpl : ViewItemsFactory {
    companion object {
        private const val gitHubRepoItemView = R.layout.view_item_github_repo
        private const val gitHubRepoItemOtherView = R.layout.view_item_github_repo_other
    }

    override fun type(gitHubRepoItem: GitHubRepoItem): Int = gitHubRepoItemView
    override fun type(gitHubRepoItemOther: GitHubRepoItemOther): Int = gitHubRepoItemOtherView

    override fun holder(type: Int, view: View): BetterViewHolder<*> =
        when (type) {
            gitHubRepoItemView -> GitHubRepoItemHolder(view)
            gitHubRepoItemOtherView -> GitHubRepoItemOtherHolder(view)
            else -> throw RuntimeException("Illegal view type")
        }
}