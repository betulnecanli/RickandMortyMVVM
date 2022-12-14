package com.betulnecanli.rickandmortymvvm.data.remote

import com.betulnecanli.rickandmortymvvm.data.models.ListResponse
import com.betulnecanli.rickandmortymvvm.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun searchCharacters(
        @Query("name")
        searchQuery : String,
        @Query("page")
        page : Int,
        @Query("status")
        status : String
    ) : Response<ListResponse>




}