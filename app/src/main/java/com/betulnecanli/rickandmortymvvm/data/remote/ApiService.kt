package com.betulnecanli.rickandmortymvvm.data.remote

import com.betulnecanli.rickandmortymvvm.data.models.ListResponse
import com.betulnecanli.rickandmortymvvm.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun getAllCharacters(
        @Query("page") page : Int
    ) : Response<ListResponse>
}