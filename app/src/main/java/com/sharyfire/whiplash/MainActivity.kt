package com.sharyfire.whiplash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sharyfire.whiplash.entity.UnsplashPhoto
import com.sharyfire.whiplash.network.BASE_URL
import com.sharyfire.whiplash.network.WhiplashApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(WhiplashApi::class.java)
        service.getPhotos().enqueue(object: Callback<List<UnsplashPhoto>> {
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
