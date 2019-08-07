package com.sharyfire.whiplash.data

import com.sharyfire.whiplash.network.WhiplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(private val api: WhiplashApi) {
    fun getPhotos() = api.getPhotos()
}