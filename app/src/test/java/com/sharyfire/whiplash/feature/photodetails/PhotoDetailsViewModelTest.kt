package com.sharyfire.whiplash.feature.photodetails

import com.sharyfire.whiplash.entity.api.UnsplashPhoto
import com.sharyfire.whiplash.entity.api.Urls
import com.sharyfire.whiplash.entity.api.User
import com.sharyfire.whiplash.testutils.BaseViewModelTest
import com.sharyfire.whiplash.testutils.LiveDataTestObserver
import com.sharyfire.whiplash.testutils.whenever
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import java.lang.RuntimeException

class PhotoDetailsViewModelTest: BaseViewModelTest() {
    private val photoId = "photoId"

    private val getPhoto = mock(GetPhoto::class.java)
    private lateinit var viewModel : PhotoDetailsViewModel

    private lateinit var screenStateObserver: LiveDataTestObserver<PhotoDetailsViewModel.ScreenState>

    private fun initViewModel() {
        viewModel = PhotoDetailsViewModel(getPhoto)
    }

    @Test
    fun `set data state when success response from useCase`() {
        // arrange
        whenever(getPhoto.execute(photoId)).thenReturn(Observable.just(
            UnsplashPhoto(urls = Urls(regular = "regular"), user = User(name = "name"))
        ))
        initViewModel()
        screenStateObserver = LiveDataTestObserver(viewModel.screenState)

        // act
        viewModel.init(photoId)

        // assert
        val result = screenStateObserver.values
        val expected = listOf(
            PhotoDetailsViewModel.ScreenState(isLoading = false),
            PhotoDetailsViewModel.ScreenState(isLoading = true, errorType = null),
            PhotoDetailsViewModel.ScreenState(displayablePhoto = DisplayableDetailsPhoto("regular", "name"), isLoading = false)
        )
        assertEquals(result, expected)
    }

    @Test
    fun `set error state when error response from useCase`() {
        // arrange
        whenever(getPhoto.execute(photoId)).thenReturn(Observable.fromCallable { throw RuntimeException()})
        initViewModel()
        screenStateObserver = LiveDataTestObserver(viewModel.screenState)

        // act
        viewModel.init(photoId)

        // assert
        val result = screenStateObserver.values
        val expected = listOf(
            PhotoDetailsViewModel.ScreenState(isLoading = false),
            PhotoDetailsViewModel.ScreenState(isLoading = true, errorType = null),
            PhotoDetailsViewModel.ScreenState(isLoading = false, errorType = PhotoDetailsViewModel.ErrorType.GENERAL_ERROR)
        )
        assertEquals(result, expected)
    }

    @Test
    fun `set error state when photoId is invalid`() {
        // arrange
        initViewModel()
        screenStateObserver = LiveDataTestObserver(viewModel.screenState)

        // act
        viewModel.init(null)

        // assert
        val result = screenStateObserver.values
        val expected = listOf(
            PhotoDetailsViewModel.ScreenState(isLoading = false),
            PhotoDetailsViewModel.ScreenState(isLoading = false, errorType = PhotoDetailsViewModel.ErrorType.FINISH_SCREEN)
        )
        assertEquals(result, expected)
    }
}