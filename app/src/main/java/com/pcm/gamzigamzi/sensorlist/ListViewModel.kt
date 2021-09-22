package com.pcm.gamzigamzi.sensorlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {
    private val repo = Repo()
    fun fetchData(): LiveData<MutableList<Sensor>> {
        val mutableData = MutableLiveData<MutableList<Sensor>>()
        repo.getData().observeForever{
            mutableData.value = it
        }
        return mutableData
    }
}