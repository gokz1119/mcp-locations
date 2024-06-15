package com.gail.mcp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gail.mcp.model.enums.Destinations
import com.gail.mcp.ui.screens.FilePickerView
import com.gail.mcp.ui.screens.HomeScreenView
import com.gail.mcp.ui.screens.SearchResultScreen
import com.gail.mcp.viewmodel.MCPViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MCPNavigationGraph(
    navController: NavHostController,
    startDestination: String,
    viewModel: MCPViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Destinations.FILE_PICKER.name) {
            FilePickerView(
                viewModel = viewModel
            )
        }

        composable(route = Destinations.HOME.name) {
            HomeScreenView(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(route = Destinations.SEARCH_RESULT.name) {
            SearchResultScreen(
                viewModel = viewModel
            )
        }
    }
}