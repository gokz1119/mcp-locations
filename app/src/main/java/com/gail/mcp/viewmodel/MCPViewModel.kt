package com.gail.mcp.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gail.mcp.repository.MCPRepository
import com.gail.mcp.state.MCPUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MCPViewModel(application: Application): ViewModel() {
    private val repository = MCPRepository(application)

    private val _mcpData = MutableStateFlow(MCPUiState.Loading)
    val mcpData: StateFlow<MCPUiState> = _mcpData.asStateFlow()

    fun loadMcpDataFromCSV(context: Context, uri: Uri) {
        viewModelScope.launch {
            val mcpLocations = repository.readCsvFromUri(context, uri)
            repository.insertAll(mcpLocations)
        }
    }
}

class MCPViewModelFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MCPViewModel(application) as T
    }
}