package com.jaimegc.agilemobilechallenge.ui.model.mapper

import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo
import com.jaimegc.agilemobilechallenge.ui.model.GitHubRepoItem
import com.jaimegc.agilemobilechallenge.ui.model.viewmodel.GitHubRepoItemViewModel
import com.jaimegc.agilemobilechallenge.ui.model.viewmodel.ItemViewModel


class GitHubRepoToGitHubRepoViewModelMapper {
    operator fun invoke(items: List<GitHubRepo>): List<ItemViewModel> =
        items.map {
            GitHubRepoItemViewModel(GitHubRepoItem(it.name, it.language))
        }
}