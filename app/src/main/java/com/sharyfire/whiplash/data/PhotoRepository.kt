package com.sharyfire.whiplash.data

import com.sharyfire.whiplash.network.WhiplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(private val api: WhiplashApi) {
    fun getPhotos(page: Int, perPage: Int) = api.getPhotos(page, perPage)
    fun getPhoto(id: String) = api.getPhoto(id)
}