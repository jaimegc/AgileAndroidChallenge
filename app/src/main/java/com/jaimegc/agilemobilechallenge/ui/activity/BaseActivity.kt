package com.jaimegc.agilemobilechallenge.ui.activity

import androidx.lifecycle.LifecycleObserver
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.kodein.di.KodeinAware

abstract class BaseActivity : AppCompatActivity(), KodeinAware {

    abstract val layoutId: Int
    abstract val presenter: LifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        lifecycle.addObserver(presenter)
        preparePresenter(intent)
    }

    open fun preparePresenter(intent: Intent?) {}
}