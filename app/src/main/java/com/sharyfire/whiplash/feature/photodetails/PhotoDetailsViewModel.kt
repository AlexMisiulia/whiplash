package com.sharyfire.whiplash.feature.photodetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sharyfire.whiplash.utils.addToCompositeDisposable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

private const val TAG = "PhotoDetailsViewModel"

class PhotoDetailsViewModel @Inject constructor(private val getPhoto: GetPhoto): ViewModel() {
    private lateinit var photoId: String

    data class ScreenState(
        val displayablePhoto: DisplayableDetailsPhoto? = null,
        val isLoading: Boolean,
        val errorType: ErrorType? = null
    )

    enum class ErrorType {
        FINISH_SCREEN,
        GENERAL_ERROR,
    }

    private val _screenState = MutableLiveData<ScreenState>()
        .apply { value = ScreenState(isLoading = false) }

    private val compositeDisposable = CompositeDisposable()
    val screenState: LiveData<ScreenState> get() = _screenState

    fun init(photoId: String?) {
        if(photoId == null) {
            setState(getCurrState().copy(errorType = ErrorType.FINISH_SCREEN))
            return
        }

        this.photoId = photoId
        loadPhoto()
    }

    fun loadPhoto() {
        setState(getCurrState().copy(isLoading = true, errorType = null))

        getPhoto.execute(photoId)
            .subscribe({
                val displayablePhoto = DisplayableDetailsPhoto(it.urls.regular, it.user.name)
                setState(getCurrState().copy(displayablePhoto = displayablePhoto, isLoading = false))

            }, {
                Log.e(TAG, "error during getting photos", it)
                setState(getCurrState().copy(isLoading = false, errorType = ErrorType.GENERAL_ERROR))
            })
            .addToCompositeDisposable(compositeDisposable)
    }

    private fun getCurrState() : ScreenState = _screenState.value!!

    private fun setState(screenState: ScreenState) {
        _screenState.value = screenState
    }
}