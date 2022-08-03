package com.betulnecanli.rickandmortymvvm.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.betulnecanli.rickandmortymvvm.data.models.Details
import com.betulnecanli.rickandmortymvvm.data.remote.ApiService
import java.lang.Exception

class RickandMortyPagingSource(
    private val apiService: ApiService
    ) : PagingSource<Int, Details>() {
    override fun getRefreshKey(state: PagingState<Int, Details>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, Details> {

        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getAllCharacters(currentPage)
            val responseData = mutableListOf<Details>()
            val data = response.body()?.details ?: emptyList()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}