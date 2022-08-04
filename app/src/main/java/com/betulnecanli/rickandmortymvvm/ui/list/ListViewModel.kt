package com.betulnecanli.rickandmortymvvm.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.betulnecanli.rickandmortymvvm.data.models.Details
import com.betulnecanli.rickandmortymvvm.data.remote.ApiService
import com.betulnecanli.rickandmortymvvm.paging.RickandMortyPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(
    private val apiService: ApiService
): ViewModel() {



    val listData = Pager(PagingConfig(pageSize = 1)){
            RickandMortyPagingSource(apiService)
    }.flow.cachedIn(viewModelScope)


    fun openCharacterDetails(details: Details){


    }

}