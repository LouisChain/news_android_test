package com.npc.news.ui.details

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.npc.news.R
import com.npc.news.models.ContentSection
import com.squareup.picasso.Picasso

class PhotoHolder(view: View): RecyclerView.ViewHolder(view){
    val tvCaption: TextView
    val ivPhoto: ImageView

    init {
        ivPhoto = view.findViewById(R.id.ivPhoto)
        tvCaption = view.findViewById(R.id.tvCaption)
    }

    fun bind(section: ContentSection?){
        section?.let {
            if (it.content != null && it.content!!.caption != null){
                tvCaption.text = it.content!!.caption
            }
            if (it.content != null && it.content!!.href != null){
                Picasso.get()
                    .load(it.content!!.href!!)
                    .placeholder(R.drawable.holder)
                    .error(R.drawable.holder)
                    .resize(400, 400)
                    .centerInside()
                    .into(ivPhoto)
            }
        }
    }
}
