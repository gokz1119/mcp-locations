package com.gail.mcp.viewmodel

import androidx.lifecycle.ViewModel
import com.gail.mcp.state.MCPUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MCPViewModel: ViewModel() {
    private val _mcpData = MutableStateFlow(MCPUiState.Loading)
    val mcpData: StateFlow<MCPUiState> = _mcpData.asStateFlow()
}