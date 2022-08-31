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
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: RickandMortyRepository
): ViewModel() {

    private val taskEventChannel = Channel<TaskEvent>()
    val taskEvent = taskEventChannel.receiveAsFlow()

    private val currentQuery =MutableLiveData("")
    val characters = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)

    }

    fun searchCharacter(query: String){
        currentQuery.value = query
    }



    fun openCharacterDetails(details: Details) = viewModelScope.launch {
        taskEventChannel.send(TaskEvent.NavigateToDetailScreen(details))

    }



    private fun handleSearchCharacter(response : Response<ListResponse>) : Resource<ListResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }



    sealed class TaskEvent{

        data class NavigateToDetailScreen(val details: Details) : TaskEvent()

    }

}