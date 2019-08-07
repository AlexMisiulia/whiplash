package com.sharyfire.whiplash.di

import com.sharyfire.whiplash.feature.photolist.PhotoListActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(photoListActivity: PhotoListActivity)
}