package com.sharyfire.whiplash.testutils

import androidx.lifecycle.LiveData

class LiveDataTestObserver<T>(liveData: LiveData<T>) {
    val values = ArrayList<T>()

    init {
        liveData.observeForever {
            values.add(it)
        }
    }
}