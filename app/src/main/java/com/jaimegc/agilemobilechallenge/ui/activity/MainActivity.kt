package com.jaimegc.agilemobilechallenge.ui.activity

import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import com.jaimegc.agilemobilechallenge.R
import com.jaimegc.agilemobilechallenge.common.extensions.*
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

    private lateinit var progress: ProgressBar
    private lateinit var editSearch: EditText
    private lateinit var buttonSearch: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeViews()
    }

    private fun initializeViews() {
        editSearch = findViewById(R.id.edit_search)
        buttonSearch = findViewById(R.id.button_search)
        progress = findViewById(R.id.progress)

        searchDisabled()

        editSearch.afterTextChanged { text ->
            if (text.isNotEmpty()) searchEnabled() else searchDisabled()
        }

        editSearch.searchKeyboardClicked { text ->
            presenter.getGitHubReposByUser(text)
        }

        buttonSearch.setOnClickListener {
            hideKeyboard()
            presenter.getGitHubReposByUser(editSearch.text.toString())
        }
    }

    override fun goDetail(items: List<GitHubRepo>) {
        DetailActivity.open(this, items)
    }

    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.hide()
    }

    override fun showUserNotFound() {
        DialogUtils.showUserNotFound(this)
    }

    override fun showUserUnknown() {
        DialogUtils.showUserUnknown(this)
    }

    override fun showError() {
        DialogUtils.showError(this)
    }

    override fun showReposNotFound() {
        DialogUtils.showReposNotFound(this)
    }

    private fun searchEnabled() {
        buttonSearch.isEnabled = true
        buttonSearch.setBackgroundResource(R.drawable.button_rounded_enabled)
    }

    private fun searchDisabled() {
        buttonSearch.isEnabled = false
        buttonSearch.setBackgroundResource(R.drawable.button_rounded_disabled)
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