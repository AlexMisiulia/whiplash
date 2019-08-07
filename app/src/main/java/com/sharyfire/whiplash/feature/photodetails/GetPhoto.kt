package com.sharyfire.whiplash.feature.photodetails

import com.sharyfire.whiplash.data.PhotoRepository
import com.sharyfire.whiplash.entity.api.UnsplashPhoto
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetPhoto @Inject constructor(private val repository: PhotoRepository) {

     fun execute(id: String): Observable<UnsplashPhoto> {
        return repository.getPhoto(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}