package com.gail.mcp.state

import com.gail.mcp.model.MCPLocationData

sealed class MCPUiState {
    data object Loading : MCPUiState()

    data object Initial : MCPUiState()

    data class Success(
        val mcpLocationData: MCPLocationData? = null
    ) : MCPUiState()

    data object Error : MCPUiState()
}