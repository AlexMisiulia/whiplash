package com.sharyfire.whiplash

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sharyfire.whiplash.di.Injector
import com.sharyfire.whiplash.entity.UnsplashPhoto
import com.sharyfire.whiplash.network.WhiplashApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    @Inject lateinit var whiplashApi: WhiplashApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Injector.appComponent.inject(this)


        whiplashApi.getPhotos().enqueue(object: Callback<List<UnsplashPhoto>> {
            override fun onFailure(call: Call<List<UnsplashPhoto>>, t: Throwable) {
                Log.e(TAG, "error during getting photos", t)
            }

            override fun onResponse(
                    call: Call<List<UnsplashPhoto>>, response: Response<List<UnsplashPhoto>>
            ) {
                Log.d(TAG, "Success response = ${response.body()}")
            }
        })
    }
}
