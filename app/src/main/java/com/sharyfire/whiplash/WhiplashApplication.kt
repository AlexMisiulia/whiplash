package com.sharyfire.whiplash

import android.app.Application
import com.sharyfire.whiplash.di.Injector

class WhiplashApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
    }
}