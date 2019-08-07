package com.sharyfire.whiplash.feature.photolist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sharyfire.whiplash.utils.addToCompositeDisposable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

private const val TAG = "PhotoListViewModel"

class PhotoListViewModel @Inject constructor(private val getPhotos: GetPhotos) : ViewModel() {
    data class ScreenState(
        val displayablePhotos: List<DisplayablePhoto>,
        val isLoading: Boolean,
        val isSwipeRefresh: Boolean,
        val isError: Boolean
    )

    private val _screenState = MutableLiveData<ScreenState>()
        .apply { value = ScreenState(emptyList(), isLoading = false, isError = false, isSwipeRefresh = false) }

    private val compositeDisposable = CompositeDisposable()
    val screenState: LiveData<ScreenState> get() = _screenState

    init {
        loadPhotos()
    }

    fun loadPhotos(isSwipeRefresh: Boolean = false) {
        setState(getCurrState().copy(isSwipeRefresh = isSwipeRefresh, isLoading = !isSwipeRefresh, isError = false))

        getPhotos.execute()
            .subscribe({
                val displayablePhotos = it.map { photo ->
                    DisplayablePhoto(
                        photo.urls.regular,
                        photo.id
                    )
                }
                setState(getCurrState().copy(displayablePhotos = displayablePhotos, isLoading = false, isSwipeRefresh = false))

            }, {
                Log.e(TAG, "error during getting photos", it)
                setState(getCurrState().copy(isLoading = false, isSwipeRefresh = false, isError = true))
            })
            .addToCompositeDisposable(compositeDisposable)
    }

    private fun getCurrState() : ScreenState = _screenState.value!!

    private fun setState(screenState: ScreenState) {
        _screenState.value = screenState
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
