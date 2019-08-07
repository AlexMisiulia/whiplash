package com.sharyfire.whiplash

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sharyfire.whiplash.di.Injector
import com.sharyfire.whiplash.network.WhiplashApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    @Inject lateinit var whiplashApi: WhiplashApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Injector.appComponent.inject(this)


        whiplashApi.getPhotos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            Log.d(TAG, "Success response = $it")

        }, {
            Log.e(TAG, "error during getting photos", it)

        })
    }
}
