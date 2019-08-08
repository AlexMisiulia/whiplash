package com.sharyfire.whiplash.feature.photodetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sharyfire.whiplash.R
import com.sharyfire.whiplash.di.Injector
import com.sharyfire.whiplash.di.ViewModelFactory
import kotlinx.android.synthetic.main.activity_photo_details.*
import javax.inject.Inject

private const val EXTRA_PHOTO_ID = "photoId"

class PhotoDetailsActivity: AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context, photoId: String) : Intent {
            return Intent(context, PhotoDetailsActivity::class.java).apply {
                putExtra(EXTRA_PHOTO_ID, photoId)
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<PhotoDetailsViewModel>
    lateinit var viewModel: PhotoDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)

        val photoId = intent.getStringExtra(EXTRA_PHOTO_ID)

        Injector.appComponent.inject(this)
        initView()
        initViewModel(photoId)
    }

    private fun initViewModel(photoId: String?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PhotoDetailsViewModel::class.java)
        viewModel.init(photoId)

        viewModel.screenState.observe(this, Observer {
            render(it)
        })
    }

    private fun render(state: PhotoDetailsViewModel.ScreenState) {
        state.displayablePhoto?.let {
            Glide.with(this).load(it.photoUrl).into(photoImageView)
            userNameTextView.text = it.authorName
        }

        swipeRefresh.isRefreshing = state.isLoading

        when (state.errorType) {
            PhotoDetailsViewModel.ErrorType.GENERAL_ERROR -> {
                showErrorSnackbar()
            }

            PhotoDetailsViewModel.ErrorType.FINISH_SCREEN -> {
                Toast.makeText(this, R.string.critical_error_msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showErrorSnackbar() {
        Snackbar.make(swipeRefresh, R.string.error_msg, Snackbar.LENGTH_SHORT)
            .setAction(R.string.retry) {
                viewModel.loadPhoto()
            }
            .show()
    }

    private fun initView() {
        swipeRefresh.setOnRefreshListener {
            viewModel.loadPhoto()
        }
    }
}