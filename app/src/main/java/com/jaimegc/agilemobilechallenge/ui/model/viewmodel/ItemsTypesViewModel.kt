package com.jaimegc.agilemobilechallenge.ui.model.viewmodel

import com.jaimegc.agilemobilechallenge.ui.model.GitHubRepoItem
import com.jaimegc.agilemobilechallenge.ui.adapter.holder.factory.ViewItemsFactory
import com.jaimegc.agilemobilechallenge.ui.model.GitHubRepoItemOther

class GitHubRepoItemViewModel(
    val gitHubRepoItem: GitHubRepoItem
) : ItemViewModel {
    override val id: String
        get() = gitHubRepoItem.repository
    override operator fun invoke(viewItemsFactory: ViewItemsFactory): Int = viewItemsFactory.type(gitHubRepoItem)
}

class GitHubRepoItemOtherViewModel(
    val gitHubRepoItemOther: GitHubRepoItemOther
) : ItemViewModel {
    override val id: String
        get() = gitHubRepoItemOther.repository
    override operator fun invoke(viewItemsFactory: ViewItemsFactory): Int = viewItemsFactory.type(gitHubRepoItemOther)
}