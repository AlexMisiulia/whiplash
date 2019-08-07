package com.sharyfire.whiplash.network

import com.sharyfire.whiplash.entity.UnsplashPhoto
import io.reactivex.Observable
import retrofit2.http.GET

const val BASE_URL = "https://api.unsplash.com/"
const val CLIENT_ID = "6287def7d3a42981c4e9e72b3acd5128685b73b80e96fdc73dbd8584042f8555"

interface WhiplashApi {

    @GET("/photos?client_id=$CLIENT_ID")
    fun getPhotos() : Observable<List<UnsplashPhoto>>
}