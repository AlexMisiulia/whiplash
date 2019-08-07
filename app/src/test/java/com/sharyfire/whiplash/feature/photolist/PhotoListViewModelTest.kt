package com.sharyfire.whiplash.feature.photolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sharyfire.whiplash.entity.api.UnsplashPhoto
import com.sharyfire.whiplash.entity.api.Urls
import com.sharyfire.whiplash.entity.ui.DisplayablePhoto
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import com.sharyfire.whiplash.testutils.RxImmediateSchedulerRule
import org.junit.Rule
import org.junit.rules.TestRule

class PhotoListViewModelTest {
    @Rule @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Rule @JvmField
    val schedulersRule = RxImmediateSchedulerRule()

    private val getPhotos = mock(GetPhotos::class.java)
    private lateinit var viewModel: PhotoListViewModel

    private fun initViewModel() {
        viewModel = PhotoListViewModel(getPhotos)
    }

    @Test
    fun `display photos received from api`() {
        // arrange
        val apiList = listOf(UnsplashPhoto(Urls("full", "raw", "photoUrl", "small", "thumb")))
        `when`(getPhotos.execute()).thenReturn(Observable.just(apiList))

        // act
        initViewModel()
        val result = viewModel.screenState.value

        // assert
        val expected = PhotoListViewModel.ScreenState(
            listOf(DisplayablePhoto("photoUrl"))
        )
        Assert.assertEquals(expected, result)
    }

}