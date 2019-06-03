package com.jaimegc.agilemobilechallenge.ui.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import com.jaimegc.agilemobilechallenge.R
import com.jaimegc.agilemobilechallenge.common.extensions.afterTextChanged
import com.jaimegc.agilemobilechallenge.common.extensions.hide
import com.jaimegc.agilemobilechallenge.common.extensions.searchKeyboardClicked
import com.jaimegc.agilemobilechallenge.common.extensions.show
import com.jaimegc.agilemobilechallenge.common.utils.DialogUtils
import com.jaimegc.agilemobilechallenge.di.KodeinModules
import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo
import com.jaimegc.agilemobilechallenge.ui.presenter.MainPresenter
import org.kodein.di.Copy
import org.kodein.di.android.kodein
import org.kodein.di.android.retainedSubKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider


class MainActivity : BaseActivity(), MainPresenter.View {

    override val presenter: MainPresenter by instance()
    override val layoutId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeViews()
    }

    private fun initializeViews() {
        searchDisabled()

        edit_search.afterTextChanged { text ->
            if (text.isNotEmpty()) searchEnabled() else searchDisabled()
        }

        edit_search.searchKeyboardClicked { text ->
            presenter.getGitHubReposByUser(text)
        }

        button_search.setOnClickListener {
            presenter.getGitHubReposByUser(edit_search.text.toString())
        }
    }

    override fun goDetail(items: List<GitHubRepo>) {

    }

    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.hide()
    }

    override fun showUserNotFoundError() {
        DialogUtils.showDialogUserNotFound(this)
    }

    override fun showError() {
        DialogUtils.showError(this)
    }

    private fun searchEnabled() {
        button_search.isEnabled = true
        button_search.setBackgroundResource(R.drawable.button_rounded_enabled)
    }

    private fun searchDisabled() {
        button_search.isEnabled = false
        button_search.setBackgroundResource(R.drawable.button_rounded_disabled)
    }

    override val kodein by retainedSubKodein(kodein(), copy = Copy.All) {
        val modules = KodeinModules(applicationContext)
        import(modules.mainActivityModule, allowOverride = true)

        bind<MainPresenter>() with provider {
            MainPresenter(
                this@MainActivity,
                instance()
            )
        }
    }
}