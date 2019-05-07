package com.npc.news.di

import com.npc.news.api.APIServiceImpl
import com.npc.news.api.RetrofitService
import com.npc.news.ui.details.NewsDetailViewModel
import com.npc.news.ui.news.NewsViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    single { RetrofitService() }
    single { CompositeDisposable() }

    single { APIServiceImpl(get()) }

    viewModel { NewsViewModel(get(), get()) }
    viewModel { NewsDetailViewModel(get(), get()) }
}