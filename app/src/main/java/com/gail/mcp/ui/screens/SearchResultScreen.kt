package com.gail.mcp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gail.mcp.R
import com.gail.mcp.state.MCPUiState
import com.gail.mcp.viewmodel.MCPViewModel

@Composable
fun SearchResultScreen(
    viewModel: MCPViewModel
) {
    val searchString = viewModel.searchString.collectAsState()
    val searchUiState = viewModel.mcpSearchUiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.searchMcpLocation(searchString.value)
    }

    val listState = rememberLazyListState()
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
                Text(
                    text = stringResource(R.string.uh_oh_couldnt_find_the_location),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    lineHeight = 30.sp
                )
            }

            is MCPUiState.Success -> {
                val successData = searchUiState.value as MCPUiState.Success
                successData.mcpLocationData?.let {
                    LazyColumn(
                        state = listState,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Text(
                                text = stringResource(
                                    R.string.the_mcp_is_located_at,
                                    it.locationId
                                ),
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp,
                                lineHeight = 30.sp
                            )
                        }
                        item {
                            Text(
                                text = it.locationName,
                                fontSize = 30.sp,
                                lineHeight = 36.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = Color.Green
                            )
                        }
                        if (it.imageUrl.isNotEmpty()) {
                            item {
                                val painter =
                                    rememberAsyncImagePainter(
                                        model = ImageRequest
                                            .Builder(LocalContext.current)
                                            .data(it.imageUrl)
                                            .crossfade(true)
                                            .build()
                                    )
                                Image(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(400.dp)
                                        .padding(vertical = 24.dp)
                                        .clip(RoundedCornerShape(24.dp)),
                                    painter = painter,
                                    contentDescription = "MCP Location Image",
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }
                } ?: kotlin.run {
                    Text(text = stringResource(R.string.uh_oh_couldnt_find_the_location))
                }
            }
        }
    }
}