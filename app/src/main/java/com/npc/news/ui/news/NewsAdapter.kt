package com.npc.news.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.npc.news.R
import com.npc.news.models.News

class NewsAdapter : RecyclerView.Adapter<NewsHolder>(){

    interface NewsListener{
        fun onNewsClick()
    }

    lateinit var listener : NewsListener

    private var newsList: MutableList<News> = ArrayList<News>()

    fun setNewsList(newsList: List<News>){
        this.newsList = ArrayList<News>(newsList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(listener, LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))
    }

}