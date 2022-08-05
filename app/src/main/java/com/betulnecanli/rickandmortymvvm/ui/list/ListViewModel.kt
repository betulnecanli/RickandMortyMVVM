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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(
    private val apiService: ApiService
): ViewModel() {

    private val taskEventChannel = Channel<TaskEvent>()
    val taskEvent = taskEventChannel.receiveAsFlow()


    val listData = Pager(PagingConfig(pageSize = 1)){
            RickandMortyPagingSource(apiService)
    }.flow.cachedIn(viewModelScope)


    fun openCharacterDetails(details: Details) = viewModelScope.launch {
        taskEventChannel.send(TaskEvent.NavigateToDetailScreen(details))

    }



    sealed class TaskEvent{

        data class NavigateToDetailScreen(val details: Details) : TaskEvent()

    }

}