package com.jaimegc.agilemobilechallenge.ui.presenter

import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.jaimegc.agilemobilechallenge.common.extensions.weak
import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo
import com.jaimegc.agilemobilechallenge.ui.model.GitHubRepoItem
import com.jaimegc.agilemobilechallenge.ui.model.GitHubRepoItemOther
import com.jaimegc.agilemobilechallenge.ui.model.viewmodel.GitHubRepoItemOtherViewModel
import com.jaimegc.agilemobilechallenge.ui.model.viewmodel.GitHubRepoItemViewModel
import com.jaimegc.agilemobilechallenge.ui.model.viewmodel.ItemViewModel


class DetailPresenter(
    view: View
) : LifecycleObserver {

    private val view: View? by weak(view)
    private var items: List<GitHubRepo>? = null

    fun setItems(items: List<GitHubRepo>) {
        this.items = items
    }

    @OnLifecycleEvent(ON_RESUME)
    fun update() {
        view?.showLoading()
        val listItems = mutableListOf<ItemViewModel>()

        items?.let { list ->
            list.mapIndexed { index, gitHubRepo ->
                when {
                    index % 2 == 0 -> listItems.add(gitHubRepo.toViewModel())
                    else -> listItems.add(gitHubRepo.toOtherViewModel())
                }
            }
        }

        view?.showItems(listItems)
        view?.hideLoading()
    }

    private fun GitHubRepo.toViewModel(): ItemViewModel =
        GitHubRepoItemViewModel(GitHubRepoItem(name, language))

    private fun GitHubRepo.toOtherViewModel(): ItemViewModel =
        GitHubRepoItemOtherViewModel(GitHubRepoItemOther(name))

    interface View {
        fun showItems(items: List<ItemViewModel>)
        fun showLoading()
        fun hideLoading()
    }
}