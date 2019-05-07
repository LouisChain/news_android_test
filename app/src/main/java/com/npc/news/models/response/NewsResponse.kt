package com.npc.news.models.response

import com.google.gson.annotations.Expose
import com.npc.news.models.News

class NewsResponse(message: String?, statusCode: Int) : BaseResponse(message, statusCode){
    @Expose
    val items: List<News>? = null
}