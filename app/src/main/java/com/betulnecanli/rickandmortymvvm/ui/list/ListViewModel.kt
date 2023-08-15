package com.betulnecanli.rickandmortymvvm.ui.list

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.betulnecanli.rickandmortymvvm.data.models.Details
import com.betulnecanli.rickandmortymvvm.data.models.ListResponse
import com.betulnecanli.rickandmortymvvm.data.remote.ApiService
import com.betulnecanli.rickandmortymvvm.paging.RickandMortyPagingSource
import com.betulnecanli.rickandmortymvvm.repository.RickandMortyRepository
import com.betulnecanli.rickandmortymvvm.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.switchMap
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: RickandMortyRepository
) : ViewModel() {

    private val taskEventChannel = Channel<TaskEvent>()
    val taskEvent = taskEventChannel.receiveAsFlow()

    private val currentQuery = MutableLiveData("")
    private val statusSelected = MutableLiveData("")


    private val charactersFlow =
        combine(currentQuery.asFlow(), statusSelected.asFlow()) { query_, status_ ->
            Pair(query_, status_)
        }.flatMapLatest { (query_, status_) ->
            repository.getSearchResults(query_, status_).asFlow()
        }

    val characters = charactersFlow.asLiveData()


    fun searchCharacter(query: String) {
        currentQuery.value = query
    }

    fun statusChoose(status: String) {
        statusSelected.value = status
    }


    fun openCharacterDetails(details: Details) = viewModelScope.launch {
        taskEventChannel.send(TaskEvent.NavigateToDetailScreen(details))
    }

    sealed class TaskEvent {

        data class NavigateToDetailScreen(val details: Details) : TaskEvent()

    }

}