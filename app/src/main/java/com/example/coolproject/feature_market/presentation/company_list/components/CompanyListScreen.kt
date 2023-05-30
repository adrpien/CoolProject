package com.example.coolproject.feature_market.presentation.company_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coolproject.feature_market.presentation.company_list.CompanyListViewModel
import com.example.coolproject.feature_market.presentation.company_list.CompanyListEvent
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun CompanyListScreen(
    navigator: DestinationsNavigator,
    viewModel: CompanyListViewModel = hiltViewModel(),
) {

    // When the state changes, swipe refresh layout will automatically refresh
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.value.isRefreshing
    )
    val state = viewModel.state
    
    Column(Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = state.value.searchQuery,
            onValueChange = {
                viewModel.onEvent(CompanyListEvent.onSearchQueryChange(it))
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true
        )

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(CompanyListEvent.Refresh) }
        ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()){
                    items(state.value.companyList.size) { index ->
                        CompanyItem(
                            company = state.value.companyList[index],
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                           // TODO Navigate to details screen
                                },
                            )
                        if(index < state.value.companyList.size) {
                            Divider(Modifier.padding(horizontal = 8.dp))
                        }

                    }
                }
        }
    }
}