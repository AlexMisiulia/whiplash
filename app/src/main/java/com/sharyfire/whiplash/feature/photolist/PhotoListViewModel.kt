package com.sharyfire.whiplash.feature.photolist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sharyfire.whiplash.entity.ui.DisplayablePhoto
import com.sharyfire.whiplash.network.WhiplashApi
import com.sharyfire.whiplash.utils.addToCompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "PhotoListViewModel"

class PhotoListViewModel @Inject constructor(private val api: WhiplashApi) : ViewModel() {
    private val _screenState by lazy {
        loadPhotos()
        MutableLiveData<ScreenState>()
    }

    private val compositeDisposable = CompositeDisposable()
    val screenState: LiveData<ScreenState> get() = _screenState

    private fun loadPhotos() {

        api.getPhotos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val displayablePhotos = it.map { photo -> DisplayablePhoto(photo.urls.regular) }
                _screenState.value = ScreenState(displayablePhotos)

            }, {
                Log.e(TAG, "error during getting photos", it)

            })
            .addToCompositeDisposable(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    data class ScreenState(
        val displayablePhotos: List<DisplayablePhoto>
    )
}
