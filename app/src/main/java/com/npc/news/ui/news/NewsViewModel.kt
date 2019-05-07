package com.npc.news.ui.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.npc.news.api.APIServiceImpl
import com.npc.news.base.BaseViewModel
import com.npc.news.models.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsViewModel(mService: APIServiceImpl, compositeDisposable: CompositeDisposable):  BaseViewModel(mService, compositeDisposable){

    var news: MutableLiveData<List<News>> = MutableLiveData<List<News>>()

    fun getNewsList(){
        addDisposable(
            service.getNewsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { res ->
                        news.value = res.items
                    },
                    { error ->
                        errorMessage.value = error.message
                     }
                )
        )
    }
}