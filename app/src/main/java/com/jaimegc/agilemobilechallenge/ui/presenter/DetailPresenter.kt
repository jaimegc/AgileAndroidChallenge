package com.jaimegc.agilemobilechallenge.ui.presenter

import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.jaimegc.agilemobilechallenge.common.extensions.weak
import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo
import com.jaimegc.agilemobilechallenge.ui.model.mapper.GitHubRepoToGitHubRepoViewModelMapper
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

        items?.let {
            view?.showItems(GitHubRepoToGitHubRepoViewModelMapper()(it))
        }

        view?.hideLoading()
    }

    interface View {
        fun showItems(items: List<ItemViewModel>)
        fun showLoading()
        fun hideLoading()
    }
}