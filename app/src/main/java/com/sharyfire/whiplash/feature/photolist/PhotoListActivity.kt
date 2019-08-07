package com.sharyfire.whiplash.feature.photolist

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sharyfire.whiplash.R
import com.sharyfire.whiplash.di.Injector
import com.sharyfire.whiplash.di.ViewModelFactory
import com.sharyfire.whiplash.utils.setVisible
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

private const val TAG = "PhotoListActivity"

class PhotoListActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory<PhotoListViewModel>
    lateinit var viewModel: PhotoListViewModel

    private val adapter = PhotoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        if(state.isError) {
            AlertDialog.Builder(this)
                .setMessage(R.string.error_msg)
                .setPositiveButton(R.string.retry) { _, _ ->
                    viewModel.loadPhotos()
                }.setNegativeButton(R.string.cancel) { _, _ ->

                }.show()
        }
    }

    private fun initView() {
        photosRecyclerView.adapter = adapter
        photosRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}



