package com.sharyfire.whiplash.feature.photolist

import com.sharyfire.whiplash.entity.api.UnsplashPhoto
import com.sharyfire.whiplash.entity.api.Urls
import com.sharyfire.whiplash.testutils.BaseViewModelTest
import com.sharyfire.whiplash.testutils.LiveDataTestObserver
import com.sharyfire.whiplash.testutils.whenever
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock
import java.util.concurrent.TimeUnit

class PhotoListViewModelTest : BaseViewModelTest() {

    private val testScheduler = TestScheduler()

    private val getPhotos = mock(GetPhotos::class.java)
    private lateinit var viewModel: PhotoListViewModel

    private lateinit var screenStateObserver: LiveDataTestObserver<PhotoListViewModel.ScreenState>

    private fun initViewModel() {
        viewModel = PhotoListViewModel(getPhotos)
        screenStateObserver = LiveDataTestObserver(viewModel.screenState)
    }

    @Test
    fun `display photos received from api`() {
        // arrange
        val apiList = listOf(UnsplashPhoto(id = "id",urls = Urls(regular = "photoUrl")))
        mockGetPhotosResponse(apiList)

        // act
        initViewModel()
        performGetPhotosResponse()

        // assert
        val expected = listOf(
            PhotoListViewModel.ScreenState(emptyList(), isLoading = true, isSwipeRefresh = false, isError = false),
            PhotoListViewModel.ScreenState(
                listOf(DisplayablePhoto("photoUrl", "id")),
                isLoading = false,
                isSwipeRefresh = false,
                isError = false
            )
        )

        Assert.assertEquals(expected, screenStateObserver.values)
    }

    private fun mockGetPhotosResponse(photoList: List<UnsplashPhoto>) {
        whenever(getPhotos.execute()).thenReturn(Observable.just(photoList).delay(1L, TimeUnit.MILLISECONDS, testScheduler))
    }

    private fun performGetPhotosResponse() {
        testScheduler.advanceTimeBy(1L, TimeUnit.MILLISECONDS)
    }

}