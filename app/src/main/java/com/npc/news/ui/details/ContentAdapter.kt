package com.npc.news.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.npc.news.models.ContentSection


class ContentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    val TEXT_TYPE: Int = 1
    val VIDEO_TYPE: Int = 2
    val PHOTO_TYPE:Int = 3
    val player: ExoPlayer

    constructor(context: Context){
        player = ExoPlayerFactory.newSimpleInstance(context)
    }

    var contents: MutableList<ContentSection?> = ArrayList<ContentSection?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TEXT_TYPE -> ContentHolder(LayoutInflater.from(parent.context).inflate(com.npc.news.R.layout.item_text, parent, false))
            VIDEO_TYPE -> VideoHolder(player, LayoutInflater.from(parent.context).inflate(com.npc.news.R.layout.item_video, parent, false))
            else -> PhotoHolder(LayoutInflater.from(parent.context).inflate(com.npc.news.R.layout.item_photo, parent, false))
        }
    }

    fun setContentsList(list: ArrayList<ContentSection?>){
        this.contents = ArrayList<ContentSection?>(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = contents.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val section: ContentSection? = contents[position]
        when(holder){
            is ContentHolder -> holder.bind(section)
            is VideoHolder -> holder.bind(section)
            is PhotoHolder -> holder.bind(section)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val section: ContentSection = contents[position]!!
        return when(section.section_type){
            1 -> TEXT_TYPE
            2 -> VIDEO_TYPE
            else -> PHOTO_TYPE
        }
    }
}