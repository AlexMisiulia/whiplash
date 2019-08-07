package com.sharyfire.whiplash.di

import android.content.Context
import com.sharyfire.whiplash.network.BASE_URL
import com.sharyfire.whiplash.network.WhiplashApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    fun providesContext() = context

    @Provides
    @Singleton
    fun provides(): WhiplashApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(WhiplashApi::class.java)
    }
}