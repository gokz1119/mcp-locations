package com.gail.mcp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gail.mcp.R
import com.gail.mcp.state.MCPUiState
import com.gail.mcp.viewmodel.MCPViewModel

@Composable
fun SearchResultScreen(
    viewModel: MCPViewModel
) {
    val searchString = viewModel.searchString.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.searchMcpLocation(searchString.value)
    }

    val searchUiState = viewModel.mcpSearchUiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (searchUiState.value) {
            MCPUiState.Loading, MCPUiState.Initial -> {
                CircularProgressIndicator()
            }

            is MCPUiState.Error -> {
                Text(text = stringResource(R.string.uh_oh_couldnt_find_the_location))
            }

            is MCPUiState.Success -> {
                val successData = searchUiState.value as MCPUiState.Success
                successData.mcpLocationData?.let {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.the_mcp_is_located_at,
                                it.locationId),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            lineHeight = 30.sp
                        )
                        Text(
                            text = it.locationName,
                            fontSize = 30.sp,
                            lineHeight = 36.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Color.Green
                        )
                    }
                } ?: kotlin.run {
                    Text(text = stringResource(R.string.uh_oh_couldnt_find_the_location))
                }
            }
        }
    }
}