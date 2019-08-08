package com.sharyfire.whiplash.feature.photolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sharyfire.whiplash.R
import com.sharyfire.whiplash.di.Injector
import com.sharyfire.whiplash.di.ViewModelFactory
import com.sharyfire.whiplash.feature.photodetails.PhotoDetailsActivity
import kotlinx.android.synthetic.main.activity_photo_list.*
import javax.inject.Inject

private const val TAG = "PhotoListActivity"

class PhotoListActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory<PhotoListViewModel>
    lateinit var viewModel: PhotoListViewModel

    private lateinit var endlessScrollListener: EndlessRecyclerViewScrollListener

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
        adapter.submitContentList(state.displayablePhotos)
        adapter.setLoading(state.isLoading)
        adapter.setError(state.isError)

        swipeRefresh.isRefreshing = state.isSwipeRefresh
    }

    private fun initView() {
        photosRecyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        photosRecyclerView.layoutManager = linearLayoutManager

        endlessScrollListener = object: EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if(!adapter.isLoading()) {
                    viewModel.loadMorePhotos()
                }
            }
        }

        photosRecyclerView.addOnScrollListener(endlessScrollListener)

        swipeRefresh.setOnRefreshListener {
            viewModel.refreshPhotos()
        }
    }
}



