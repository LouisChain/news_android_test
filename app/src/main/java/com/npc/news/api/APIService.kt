package com.npc.news.api

import com.npc.news.models.NewsDetail
import com.npc.news.models.response.NewsResponse
import io.reactivex.Observable

interface APIService{
    fun getNewsList(): Observable<NewsResponse>
    fun getNewsDetail(): Observable<NewsDetail>
}