package com.example.coolproject.feature_market.presentation.company_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrpien.dictionaryapp.core.util.ResourceState
import com.example.coolproject.feature_market.domain.repository.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListViewModel @Inject constructor(
    private val repository: MarketRepository
): ViewModel() {

    var state = mutableStateOf(CompanyListState())

    private var searchJob: Job? = null

    init {
        getCompanyList(" ")
    }

    fun onEvent(event: CompanyListEvent) {
        when(event) {
            is CompanyListEvent.onSearchQueryChange -> {
                state.value = state.value.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    launch {
                        delay(500L)
                        getCompanyList(
                            query = event.query
                        )
                    }
                }
            }
            is CompanyListEvent.Refresh -> {
                getCompanyList(
                    fetchFromApi = true
                )
            }
        }
    }

    private fun getCompanyList(
        query: String = state.value.searchQuery.lowercase(),
        fetchFromApi: Boolean = false
    ) {
        viewModelScope.launch {
            repository.getCompanyList(fetchFromApi, query)
                .collect { result ->
                    when(result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let {
                                state.value = state.value.copy(companyList =  result.data)
                            }
                        }
                        ResourceState.ERROR -> Unit
                        ResourceState.LOADING -> {
                             state.value = state.value.copy(isLoading = true)
                        }
                    }

                }
        }
    }
}