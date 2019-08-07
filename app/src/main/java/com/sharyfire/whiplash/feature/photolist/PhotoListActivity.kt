package com.sharyfire.whiplash.feature.photolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sharyfire.whiplash.R
import com.sharyfire.whiplash.di.Injector
import com.sharyfire.whiplash.di.ViewModelFactory
import com.sharyfire.whiplash.feature.photodetails.PhotoDetailsActivity
import com.sharyfire.whiplash.utils.setVisible
import kotlinx.android.synthetic.main.activity_photo_list.*
import javax.inject.Inject

private const val TAG = "PhotoListActivity"

class PhotoListActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory<PhotoListViewModel>
    lateinit var viewModel: PhotoListViewModel

    private val adapter = PhotoAdapter{
        val startIntent = PhotoDetailsActivity.getStartIntent(this, it.id)
        startActivity(startIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_list)

        Injector.appComponent.inject(this)
        initView()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PhotoListViewModel::class.java)
        viewModel.screenState.observe(this, Observer {
            render(it)
        })
    }

    private fun render(state: PhotoListViewModel.ScreenState) {
        adapter.submitList(state.displayablePhotos)
        progressBar.setVisible(state.isLoading)

        swipeRefresh.isRefreshing = state.isSwipeRefresh

        if (state.isError) {
            Snackbar.make(findViewById(android.R.id.content), R.string.error_msg, Snackbar.LENGTH_SHORT)
                .setAction(R.string.retry) {
                    viewModel.loadPhotos()
                }
                .show()
        }
    }

    private fun initView() {
        photosRecyclerView.adapter = adapter
        photosRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        swipeRefresh.setOnRefreshListener {
            viewModel.loadPhotos(isSwipeRefresh = true)
        }
    }
}



