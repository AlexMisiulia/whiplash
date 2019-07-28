package com.sharyfire.whiplash.di

import com.sharyfire.whiplash.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}