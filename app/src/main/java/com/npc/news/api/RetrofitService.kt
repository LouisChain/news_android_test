package com.npc.news.api

import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.R.string
import android.util.Log
import okhttp3.*


class RetrofitService{

    private val newsService: NewsService

    constructor() {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(CustomInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.dropbox.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        newsService = retrofit.create(NewsService::class.java)
    }

    fun getNewsService(): NewsService{
        return newsService
    }

    class CustomInterceptor: Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            val originRequest = chain.request().newBuilder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
            return processResponse(chain.proceed(originRequest.build()))
        }

        fun processResponse(response: Response): Response{
            try {
                val jsonString = response.body()!!.string()
                val builder = response.newBuilder()
                builder.body(ResponseBody.create(MediaType.parse("application/json"), jsonString))
                return builder.build()
            } catch (e: Exception) {
                Log.d("TAG", "processResponse: $e")
                return response
            }
        }
    }

}