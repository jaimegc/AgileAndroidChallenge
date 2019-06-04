package com.jaimegc.agilemobilechallenge.ui.model.viewmodel

import com.jaimegc.agilemobilechallenge.ui.model.GitHubRepoItem
import com.jaimegc.agilemobilechallenge.ui.adapter.holder.factory.ViewItemsFactory

class GitHubRepoItemViewModel(
    val gitHubRepoItem: GitHubRepoItem
) : ItemViewModel() {
    override operator fun invoke(viewItemsFactory: ViewItemsFactory): Int = viewItemsFactory.type(gitHubRepoItem)
}