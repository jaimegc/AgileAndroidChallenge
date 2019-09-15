package com.jaimegc.agilemobilechallenge.ui.adapter.holder.factory

import com.jaimegc.agilemobilechallenge.ui.adapter.holder.BindHolder
import com.jaimegc.agilemobilechallenge.ui.model.GitHubRepoItem
import com.jaimegc.agilemobilechallenge.ui.model.GitHubRepoItemOther

interface ViewItemsFactory : BindHolder {
    fun type(gitHubRepoItem: GitHubRepoItem): Int
    fun type(gitHubRepoItemOther: GitHubRepoItemOther): Int
}
