package com.gail.mcp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gail.mcp.R
import com.gail.mcp.model.enums.Destinations
import com.gail.mcp.viewmodel.MCPViewModel

@Composable
fun HomeScreenView(
    viewModel: MCPViewModel,
    navController: NavHostController
) {
    var searchString by rememberSaveable {
        mutableStateOf("")
    }

    var showError = rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            value = searchString,
            onValueChange = {
                if (showError.value) {
                    showError.value = false
                }
                searchString = it
            },
            label = {
                Text(text = stringResource(R.string.enter_mcp_location_id))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
                autoCorrect = false,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                    onSearchCliked(
                        viewModel = viewModel,
                        searchString = searchString,
                        showError = showError,
                        navController = navController
                    )
                }
            )
        )

        Button(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(50)),
            onClick = {
                onSearchCliked(
                    viewModel = viewModel,
                    searchString = searchString,
                    showError = showError,
                    navController = navController
                )
            }
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                text = stringResource(R.string.search_mcp_location)
            )
        }

        if (showError.value)
            Text(
                modifier = Modifier
                    .padding(top = 16.dp),
                text = stringResource(R.string.please_enter_a_location_id),
                color = Color.Red
            )

    }
}

private fun onSearchCliked(
    viewModel: MCPViewModel,
    searchString: String,
    showError: MutableState<Boolean>,
    navController: NavHostController
) {
    if (searchString.isEmpty())
        showError.value = true
    else {
        viewModel.setSearchString(searchString)
        navController.navigate(Destinations.SEARCH_RESULT.name)
    }
}