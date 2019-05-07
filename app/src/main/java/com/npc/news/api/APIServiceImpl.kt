package com.npc.news.api

import com.npc.news.models.NewsDetail
import com.npc.news.models.response.BaseResponse
import com.npc.news.models.response.NewsResponse
import io.reactivex.*
import retrofit2.Call

import java.io.IOException

class APIServiceImpl(service: RetrofitService) : APIService {

    private val newsService: NewsService = service.getNewsService()

    private fun handleResponse(call: Call<out BaseResponse>, emitter: ObservableEmitter<out BaseResponse>) {

        try {
            val response = call.execute()
            if (response.code() == 200) {
                if (!emitter.isDisposed ) {
                    (emitter as ObservableEmitter<BaseResponse>).onNext(response.body()!!)
                    emitter.onComplete()
                }
            } else {
                if (!emitter.isDisposed) {
                    if (response.errorBody() != null) {
                        emitter.onError(Throwable(response.errorBody()!!.string()))
                    } else {
                        emitter.onError(Throwable("error"))
                    }
                    emitter.onComplete()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            if (!emitter.isDisposed) {
                emitter.onError(Throwable(e.message))
            }
        }

    }


    override fun getNewsList(): Observable<NewsResponse> {
       return Observable.create{handleResponse(newsService.getNewsList(), it)}
    }

    override fun getNewsDetail(): Observable<NewsDetail> {
        return Observable.create{handleResponse(newsService.getNewsDetail(), it)}
    }
}
