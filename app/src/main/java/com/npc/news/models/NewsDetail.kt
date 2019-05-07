package com.npc.news.models

import com.npc.news.models.response.BaseResponse

data class NewsDetail(val title: String?, val description: String?, val published_date: String?,
                      val publisher: Publisher?, val sections: List<ContentSection?>?, val message1: String?,
                      val statusCode1: Int?): BaseResponse(message1, statusCode1)