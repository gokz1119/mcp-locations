package com.gail.mcp

import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.rememberNavController
import com.gail.mcp.ui.theme.MCPLocationsTheme
import com.gail.mcp.viewmodel.MCPViewModel
import com.gail.mcp.viewmodel.MCPViewModelFactory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val owner = LocalViewModelStoreOwner.current!!
            val viewModel = ViewModelProvider(
                owner,
                MCPViewModelFactory(
                    LocalContext.current.applicationContext as Application
                )
            ).get(MCPViewModel::class.java)

            MCPLocationsTheme {
                val navController = rememberNavController()
                MCPNavigationGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}