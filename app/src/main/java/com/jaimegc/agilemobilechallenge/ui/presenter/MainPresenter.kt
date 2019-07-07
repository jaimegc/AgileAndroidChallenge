package com.jaimegc.agilemobilechallenge.ui.presenter

import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.jaimegc.agilemobilechallenge.common.extensions.weak
import com.jaimegc.agilemobilechallenge.domain.model.DomainError
import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo
import com.jaimegc.agilemobilechallenge.domain.usecase.GetGitHubReposByUser
import kotlinx.coroutines.*


class MainPresenter(
    view: View,
    private val getGitHubReposByUser: GetGitHubReposByUser
) : LifecycleObserver,
    CoroutineScope by MainScope() {

    private val view: View? by weak(view)

    @OnLifecycleEvent(ON_DESTROY)
    fun destroy() {
        cancel()
    }

    fun getGitHubReposByUser(name: String, forceRefresh: Boolean = false) = launch {
        view?.showLoading()
        val result = getGitHubReposByUser.invoke(name, forceRefresh)
        view?.hideLoading()

        result.fold({ handleError(it) }, { if (it.isNotEmpty()) view?.goDetail(it) else view?.showReposNotFound() })
    }

    private fun handleError(error: DomainError) {
        when(error) {
            DomainError.UserNotFoundDomainError -> view?.showUserNotFound()
            DomainError.UserUnknownDomainError -> view?.showUserUnknown()
            else -> view?.showError()
        }
    }

    interface View {
        fun goDetail(items: List<GitHubRepo>)
        fun showLoading()
        fun hideLoading()
        fun showReposNotFound()
        fun showUserNotFound()
        fun showUserUnknown()
        fun showError()
    }
}