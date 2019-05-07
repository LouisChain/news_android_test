package com.npc.news.api

import com.npc.news.models.NewsDetail
import com.npc.news.models.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET

interface NewsService{
    @GET("/s/fy6ny7syutxl1yd/newsfeed.json?dl=1")
    fun getNewsList(): Call<NewsResponse>

    @GET("/s/v83n38kvsm6qw62/detail.json?dl=1")
    fun getNewsDetail(): Call<NewsDetail>
}