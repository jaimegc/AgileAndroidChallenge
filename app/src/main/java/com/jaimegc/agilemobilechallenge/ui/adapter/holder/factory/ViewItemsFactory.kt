package com.jaimegc.agilemobilechallenge.ui.adapter.holder.factory

import com.jaimegc.agilemobilechallenge.ui.adapter.holder.BindHolder
import com.jaimegc.agilemobilechallenge.ui.model.GitHubRepoItem

interface ViewItemsFactory : BindHolder {
    fun type(gitHubRepoItem: GitHubRepoItem): Int
}
