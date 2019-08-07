package com.sharyfire.whiplash.di

import android.content.Context

object Injector {
    private lateinit var _appComponent: AppComponent
    val appComponent: AppComponent get() = _appComponent

    fun init(context: Context) {
        _appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(context))
                .build()
    }

}