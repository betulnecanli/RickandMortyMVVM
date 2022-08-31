package com.betulnecanli.rickandmortymvvm.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.betulnecanli.rickandmortymvvm.data.models.Details
import com.betulnecanli.rickandmortymvvm.data.remote.ApiService
import java.lang.Exception

private const val STARTING_PAGE_INDEX = 1

class RickandMortyPagingSource(
    private val apiService: ApiService,
    private val query : String
    ) : PagingSource<Int, Details>() {
    override fun getRefreshKey(state: PagingState<Int, Details>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Details> {

        return try {
            val currentPage = params.key ?: STARTING_PAGE_INDEX
            val response = apiService.searchCharacters(query, currentPage)
            val responseData = mutableListOf<Details>()
            val data = response.body()?.details ?: emptyList()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = if(data.isEmpty()) null else currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}