package com.example.coolproject.feature_market.presentation.company_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coolproject.feature_market.presentation.company_details.CompanyDetailsViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun CompanyDetailsScreen(
    symbol: String,
    companyDetailsViewModel: CompanyDetailsViewModel = hiltViewModel()
){

    val state = companyDetailsViewModel.state

    if(state.error == null)  {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.DarkGray)
        ) {
            state.company?.let { companyDetails  ->
                 Text(
                     text = companyDetails.name,
                     fontSize = 16.sp,
                     fontWeight = FontWeight.Bold,
                     overflow = TextOverflow.Ellipsis,
                     modifier = Modifier.fillMaxWidth()
                 )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = companyDetails.symbol,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),

                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Industry: ${companyDetails.industry}",
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Country: ${companyDetails.country}",
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),

                    )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = companyDetails.description,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))

                if(state.intradayInfoList.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Market Summary")
                    Spacer(modifier = Modifier.height(16.dp))
                    CompanyChart(
                        intradayInfoList = state.intradayInfoList,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .align(CenterHorizontally)
                    )
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        if(state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text(text = "Error",
            color = MaterialTheme.colors.error
            )
        }
    }
}