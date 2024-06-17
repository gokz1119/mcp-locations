package com.gail.mcp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.rememberNavController
import com.gail.mcp.model.enums.Destinations
import com.gail.mcp.state.MCPUiState
import com.gail.mcp.ui.theme.MCPLocationsTheme
import com.gail.mcp.viewmodel.MCPViewModel
import com.gail.mcp.viewmodel.MCPViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val owner = LocalViewModelStoreOwner.current!!
            val viewModel = ViewModelProvider(
                owner,
                MCPViewModelFactory(
                    LocalContext.current.applicationContext as Application
                )
            ).get(MCPViewModel::class.java)

            val state = viewModel.mcpHomeUiState.collectAsState()
            val navController = rememberNavController()
            MCPLocationsTheme {
                when (state.value) {
                    MCPUiState.Loading -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    MCPUiState.Initial,
                    is MCPUiState.Success -> {
                        MCPNavigationGraph(
                            navController = navController,
                            startDestination = if (state.value == MCPUiState.Initial)
                                Destinations.FILE_PICKER.name
                            else
                                Destinations.HOME.name,
                            viewModel = viewModel
                        )
                    }

                    MCPUiState.Error -> {}
                }
            }
        }
    }
}