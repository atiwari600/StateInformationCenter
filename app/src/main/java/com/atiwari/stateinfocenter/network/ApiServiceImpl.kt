package com.atiwari.stateinfocenter.network

import com.atiwari.stateinfocenter.models.CountiesResponse
import com.atiwari.stateinfocenter.models.StateResponse
import com.atiwari.stateinfocenter.utils.AppUtils
import retrofit2.Response

class ApiServiceImpl:ApiService {

    override suspend fun getStates(): Response<StateResponse> {
        val stateData =
            AppUtils.getJsonFromAssets<StateResponse>("state_response_data.json")
        return Response.success(stateData)
    }

    override suspend fun getCounties(counties: Int): Response<CountiesResponse> {
        val data = AppUtils.getJsonFromAssets<CountiesResponse>("counties_response_data.json")
        val  countiesData = data?.counties?.take(counties)?.let { CountiesResponse(it) }
        return Response.success(countiesData)
    }

}