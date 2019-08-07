package com.sharyfire.whiplash.feature.photolist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sharyfire.whiplash.R
import com.sharyfire.whiplash.di.Injector
import com.sharyfire.whiplash.entity.ui.DisplayablePhoto
import com.sharyfire.whiplash.network.WhiplashApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

private const val TAG = "PhotoListActivity"

class PhotoListActivity : AppCompatActivity() {

    @Inject lateinit var whiplashApi: WhiplashApi
    private val adapter = PhotoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Injector.appComponent.inject(this)


        photosRecyclerView.adapter = adapter
        photosRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        whiplashApi.getPhotos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val displayablePhotos = it.map { photo -> DisplayablePhoto(photo.urls.regular) }
                adapter.submitList(displayablePhotos)

            }, {
            Log.e(TAG, "error during getting photos", it)

        })
    }
}



