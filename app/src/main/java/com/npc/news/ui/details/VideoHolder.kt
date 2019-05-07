package com.npc.news.ui.details

import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.npc.news.models.ContentSection


class VideoHolder(exoplayer: ExoPlayer, view: View): RecyclerView.ViewHolder(view){
    val tvCaption: TextView
    val playerView: PlayerView
    val player: ExoPlayer
    init {
        playerView = view.findViewById(com.npc.news.R.id.playerView)
        player = exoplayer
        tvCaption = view.findViewById(com.npc.news.R.id.tvCaption)
    }

    fun bind(section: ContentSection?){
        if (section != null && section.content != null && section.content!!.caption != null){
            tvCaption.text = section.content!!.caption!!
        }
        if (section != null && section.content != null && section.content!!.href != null){
             playerView.player =  player
            val dataSourceFactory = DefaultDataSourceFactory(
                playerView.context,
                Util.getUserAgent(playerView.context, "News")
            )
            val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(section.content!!.href))
            player.prepare(videoSource)
        }
    }
}