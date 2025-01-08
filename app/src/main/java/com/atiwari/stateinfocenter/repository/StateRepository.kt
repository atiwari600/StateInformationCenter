package com.atiwari.stateinfocenter.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atiwari.stateinfocenter.models.StateData
import com.atiwari.stateinfocenter.network.ApiService
import kotlinx.coroutines.delay

class StateRepository(private val apiService: ApiService) {

    private val _stateLiveData = MutableLiveData<Response<List<StateData>>>()
    val stateLiveData: LiveData<Response<List<StateData>>> get() = _stateLiveData

    private val _countiesLiveData = MutableLiveData<Response<List<String>>>()
    val countiesLiveData: LiveData<Response<List<String>>> get() = _countiesLiveData

    suspend fun fetchStates() {
        delay(500)
        val response = apiService.getStates()
        if (response.body() != null) {
            _stateLiveData.value = Response.Success(response.body()?.data)
        } else {
            _stateLiveData.value = Response.Error("Something went wrong!")
        }
    }

    suspend fun fetchCounties(counties: Int) {
        _countiesLiveData.value = Response.Loading()
        delay(200)
        val response = apiService.getCounties(counties)
        if (response.body() != null) {
            _countiesLiveData.value = Response.Success(response.body()?.counties)
        } else {
            _countiesLiveData.value = Response.Error("Something went wrong!")
        }
    }
}