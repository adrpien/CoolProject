package com.example.coolproject.feature_market.presentation.company_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrpien.dictionaryapp.core.util.ResourceState
import com.example.coolproject.feature_market.domain.repository.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


// SavedStateHandled is used to get argument passed to fragment without passing them earlier to viewmodel
@HiltViewModel
class CompanyDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MarketRepository
): ViewModel() {

    var state by mutableStateOf(CompanyDetailsState())


    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy( isLoading = true)
            val companyDetailsResult = async { repository.getCompanyDetails(symbol) }
            val intradayInfoResult = async { repository.getIntradayInfo(symbol) }
            val companyDetailsAwait = companyDetailsResult.await()
            when(companyDetailsAwait.resourceState) {
                ResourceState.SUCCESS -> {
                    state = state.copy(
                        company = companyDetailsAwait.data,
                        isLoading = false,
                        error = null
                        )
                }
                ResourceState.ERROR -> {
                    state = state.copy(
                        error = companyDetailsAwait.message,
                        isLoading = false,
                        company = null
                    )

                }
                ResourceState.LOADING -> Unit
            }

            val intradayInfoAwait = intradayInfoResult.await()
            when(intradayInfoAwait.resourceState) {
                ResourceState.SUCCESS -> {
                    state = state.copy(
                        intradayInfoList = intradayInfoAwait.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                ResourceState.ERROR -> {
                    state = state.copy(
                        error = intradayInfoAwait.message,
                        isLoading = false
                    )

                }
                ResourceState.LOADING -> Unit
            }
        }
    }

}