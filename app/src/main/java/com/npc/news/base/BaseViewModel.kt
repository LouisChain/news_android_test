package com.npc.news.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.npc.news.api.APIServiceImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(val service: APIServiceImpl, val compositeDisposable: CompositeDisposable): ViewModel(){

    var errorMessage: MutableLiveData<String> = MutableLiveData<String>()

    protected fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }
}