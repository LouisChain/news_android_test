package com.npc.news.ui.news

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.npc.news.R
import com.npc.news.models.News
import com.npc.news.utils.DateUtils
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.ref.WeakReference

class NewsHolder(val listener: NewsAdapter.NewsListener, val view: View): RecyclerView.ViewHolder(view){
    val listenerReference: WeakReference<NewsAdapter.NewsListener>
    val ivPublish : CircleImageView
    val tvPublish: TextView
    val tvTime: TextView
    val tvTitle: TextView
    val tvContent: TextView

    init {
        listenerReference = WeakReference<NewsAdapter.NewsListener>(listener)
        ivPublish = view.findViewById(R.id.ivPublish)
        tvPublish = view.findViewById(R.id.tvPublish)
        tvTime = view.findViewById(R.id.tvTime)
        tvTitle = view.findViewById(R.id.tvTitle)
        tvContent = view.findViewById(R.id.tvContent)
        view.setOnClickListener({
            if (listenerReference != null && listenerReference.get() != null){
                listenerReference.get()!!.onNewsClick()
            }
        })
    }

    fun bind(news: News?){
        news?.let {
            if (it.title != null){
                tvTitle.text = it.title
            }
            if (it.description != null){
                tvContent.text = it.description
            }else{
                tvContent.text = "..."
            }
            if (it.published_date != null){
                tvTime.text = DateUtils.getDisplayTime(tvTime.context, it.published_date, DateUtils.DATE_TIME_SERVER_FORMAT_2)
            }
            if (it.publisher != null){
                if (it.publisher.name != null){
                    tvPublish.text = it.publisher.name
                }
                if (it.publisher.icon != null){
                    Picasso.get().load(it.publisher.icon).placeholder(R.drawable.holder).error(R.drawable.holder)
                        .resize(200, 200)
                        .centerInside()
                        .into(ivPublish)
                }
            }
        }
    }
}