package com.jaimegc.agilemobilechallenge.ui.adapter.holder

import android.view.View
import android.widget.TextView
import com.jaimegc.agilemobilechallenge.R
import com.jaimegc.agilemobilechallenge.common.extensions.find
import com.jaimegc.agilemobilechallenge.ui.model.viewmodel.GitHubRepoItemViewModel


class GitHubRepoItemHolder(
    view: View
) : BetterViewHolder<GitHubRepoItemViewModel>(view) {
    override fun invoke(item: GitHubRepoItemViewModel) {
        val repository = itemView.find<TextView>(R.id.repository)
        val language = itemView.find<TextView>(R.id.language)

        with(item.gitHubRepoItem) {
            repository.text = this.repository
            language.text = this.language
        }
    }
}