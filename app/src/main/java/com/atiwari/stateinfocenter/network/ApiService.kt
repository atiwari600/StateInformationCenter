package com.atiwari.stateinfocenter.network

import com.atiwari.stateinfocenter.models.CountiesResponse
import com.atiwari.stateinfocenter.models.StateResponse
import retrofit2.Response

interface ApiService {

//    @GET("/states")
    suspend fun getStates() : Response<StateResponse>

//    @GET("/counties")
    suspend fun getCounties(counties: Int): Response<CountiesResponse>
}