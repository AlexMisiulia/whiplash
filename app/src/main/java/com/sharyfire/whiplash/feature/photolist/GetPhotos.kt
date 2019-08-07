package com.sharyfire.whiplash.feature.photolist

import com.sharyfire.whiplash.data.PhotoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetPhotos @Inject constructor(private val repository: PhotoRepository) {

    fun execute() = repository.getPhotos()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}