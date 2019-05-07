package com.npc.news.ui.details

import androidx.lifecycle.MutableLiveData
import com.npc.news.api.APIServiceImpl
import com.npc.news.base.BaseViewModel
import com.npc.news.models.NewsDetail
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsDetailViewModel(mService: APIServiceImpl, compositeDisposable: CompositeDisposable) : BaseViewModel(mService, compositeDisposable){
    var newsDetail: MutableLiveData<NewsDetail> = MutableLiveData<NewsDetail>()

    fun getNewsDetail(){
        addDisposable(
            service.getNewsDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {newsDetailRes ->
                        newsDetail.value = newsDetailRes
                    },
                    {error -> {
                        errorMessage.value = error.message
                    }}
                )
        )
    }
}