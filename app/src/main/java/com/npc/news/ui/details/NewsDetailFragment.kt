package com.npc.news.ui.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.npc.news.base.BaseFragment
import com.npc.news.models.NewsDetail
import kotlinx.android.synthetic.main.fragment_news_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Base64
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.npc.news.R
import com.npc.news.models.ContentSection
import com.npc.news.utils.DateUtils
import com.squareup.picasso.Picasso


class NewsDetailFragment : BaseFragment() {
    override fun getLayoutResource(): Int = R.layout.fragment_news_detail

    val model: NewsDetailViewModel by viewModel()
    lateinit var adapter: ContentAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter= ContentAdapter(context!!)
        rvContent.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvContent.adapter = adapter

        model.errorMessage.observe(this, Observer<String> { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
            swipe.isRefreshing = false

        })
        model.newsDetail.observe(this, Observer<NewsDetail> { newsDetail ->
            swipe.isRefreshing = false
            newsDetail?.let {
                if (it.title != null){
                    tvTitle.text = it.title
                }
                if (it.description != null){
                    tvContent.text = it.description
                }
                if (it.published_date != null){
                    tvTime.text = DateUtils.getDisplayTime(context, it.published_date, DateUtils.DATE_TIME_SERVER_FORMAT)
                }
                if (it.sections != null) {
                    adapter.setContentsList(ArrayList<ContentSection?>(it.sections))
                }
            }
        })
        model.getNewsDetail()
        swipe.isRefreshing = true
        swipe.setOnRefreshListener {
            model.getNewsDetail()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.player.release()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsDetailFragment()
    }
}
