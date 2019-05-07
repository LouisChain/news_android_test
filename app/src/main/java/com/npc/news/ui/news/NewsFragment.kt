package com.npc.news.ui.news

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.npc.news.R
import com.npc.news.base.BaseFragment
import com.npc.news.models.News
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewsFragment : BaseFragment(), NewsAdapter.NewsListener {
    override fun onNewsClick() {
        Navigation.findNavController(rvNews).navigate(R.id.action_newsFragment_to_newsDetailFragment)
    }

    val model: NewsViewModel by viewModel()

    override fun getLayoutResource(): Int = R.layout.fragment_news

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter: NewsAdapter = NewsAdapter()
        adapter.listener = this@NewsFragment
        rvNews.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvNews.adapter = adapter
        model.news.observe(this,
            Observer<List<News>> {news ->
                adapter.setNewsList(news)
                swipe.isRefreshing = false
            })
        model.errorMessage.observe(this,
            Observer<String> {error ->
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                swipe.isRefreshing = false
            })
        model.getNewsList()
        swipe.isRefreshing = true
        swipe.setOnRefreshListener {
            model.getNewsList()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsFragment()
    }
}
