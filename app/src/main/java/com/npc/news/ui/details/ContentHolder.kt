package com.npc.news.ui.details

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.npc.news.R
import com.npc.news.models.ContentSection

class ContentHolder(view: View): RecyclerView.ViewHolder(view){
    val tvText: TextView

    init {
        tvText = view.findViewById(R.id.tvText)
    }

    fun bind(section: ContentSection?){
        section?.let {
            if (it.content != null && it.content!!.text != null){
                tvText.text = it.content!!.text
            }
        }
    }
}