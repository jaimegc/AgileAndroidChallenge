package com.jaimegc.agilemobilechallenge.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaimegc.agilemobilechallenge.R
import com.jaimegc.agilemobilechallenge.common.extensions.*
import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo
import com.jaimegc.agilemobilechallenge.ui.adapter.ViewItemsAdapter
import com.jaimegc.agilemobilechallenge.ui.model.viewmodel.ItemViewModel
import com.jaimegc.agilemobilechallenge.ui.presenter.DetailPresenter
import de.hdodenhof.circleimageview.CircleImageView
import org.kodein.di.Copy
import org.kodein.di.android.kodein
import org.kodein.di.android.retainedSubKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class DetailActivity : BaseActivity(), DetailPresenter.View {

    companion object {
        private const val EXTRA_ITEMS = "EXTRA_ITEMS"

        fun open(context: Context, items: List<GitHubRepo>) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.addExtra(EXTRA_ITEMS, items as ArrayList<GitHubRepo>)
            context.startActivity(intent)
        }
    }

    override val presenter: DetailPresenter by instance()
    override val layoutId: Int =
        if (System.currentTimeMillis() % 2 == 0L) R.layout.activity_detail_way1 else R.layout.activity_detail_way2
    private lateinit var username: TextView
    private lateinit var profileImage: CircleImageView
    private lateinit var recycler: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var back: ImageView
    private lateinit var adapter: ViewItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeViews()
        initializeData()
        initializeAdapter()
        initializeRecyclerView()
    }

    private fun initializeViews() {
        username = findViewById(R.id.username)
        profileImage = findViewById(R.id.profile_image)
        recycler = findViewById(R.id.recycler)
        progress = findViewById(R.id.progress)
        back = findViewById(R.id.back)

        back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initializeData() {
        val item: List<GitHubRepo>? = getExtra(EXTRA_ITEMS)

        item?.let {
            presenter.setItems(it)
            username.text = item[0].owner.username
            profileImage.loadUrl(item[0].owner.avatarUrl)
        } ?: finish()
    }

    private fun initializeAdapter() {
        adapter = ViewItemsAdapter()
    }

    private fun initializeRecyclerView() {
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter
    }

    override fun showItems(items: List<ItemViewModel>) {
        adapter.submitList(items)
    }

    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.hide()
    }

    override val kodein by retainedSubKodein(kodein(), copy = Copy.All) {
        bind<DetailPresenter>() with provider {
            DetailPresenter(
                this@DetailActivity
            )
        }
    }
}