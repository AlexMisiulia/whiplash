package com.sharyfire.whiplash.feature.photolist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sharyfire.whiplash.entity.api.UnsplashPhoto
import com.sharyfire.whiplash.utils.addToCompositeDisposable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

private const val TAG = "PhotoListViewModel"
private const val PAGINATION_PER_PAGE = 50

class PhotoListViewModel @Inject constructor(private val getPhotos: GetPhotos) : ViewModel() {
    data class ScreenState(
        val displayablePhotos: List<DisplayablePhoto>,
        val isLoading: Boolean,
        val isSwipeRefresh: Boolean,
        val isError: Boolean
    )

    private val _screenState = MutableLiveData<ScreenState>().apply{
        value = ScreenState(
            emptyList(),
            isLoading = false,
            isError = false,
            isSwipeRefresh = false
        )
    }

    private var currentPaginationPage = 1

    private val compositeDisposable = CompositeDisposable()
    val screenState: LiveData<ScreenState> get() = _screenState

    init {
        loadPhotos()
    }

    fun loadMorePhotos() {
        currentPaginationPage++
        loadPhotos()
    }

    fun loadPhotos(isSwipeRefresh: Boolean = false) {
        setState(getCurrState().copy(isSwipeRefresh = isSwipeRefresh, isLoading = !isSwipeRefresh, isError = false))

        getPhotos.execute(currentPaginationPage, PAGINATION_PER_PAGE)
            .subscribe({
                onSuccessResponse(it, isSwipeRefresh)
            }, {
                onError(it)
            })
            .addToCompositeDisposable(compositeDisposable)
    }

    private fun onError(it: Throwable?) {
        Log.e(TAG, "error during getting photos", it)
        setState(getCurrState().copy(isLoading = false, isSwipeRefresh = false, isError = true))
    }

    private fun onSuccessResponse(
        it: List<UnsplashPhoto>,
        isSwipeRefresh: Boolean
    ) {
        val displayablePhotos = it.map { photo ->
            DisplayablePhoto(
                photo.urls.regular,
                photo.id
            )
        }

        val allPhotos = if (!isSwipeRefresh) {
            // add current items to old one
            getCurrState().displayablePhotos + displayablePhotos
        } else {
            // use only new items
            displayablePhotos
        }

        setState(getCurrState().copy(displayablePhotos = allPhotos, isLoading = false, isSwipeRefresh = false))
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
