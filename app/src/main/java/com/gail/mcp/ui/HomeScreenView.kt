package com.gail.mcp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gail.mcp.R
import com.gail.mcp.viewmodel.MCPViewModel

@Composable
fun HomeScreenView(
    viewModel: MCPViewModel
) {
    var searchString by rememberSaveable {
        mutableStateOf("")
    }

    var showError by rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current

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
                if (showError) {
                    showError = false
                }
                searchString = it
            },
            label = {
                Text(text = stringResource(R.string.enter_mcp_location_id))
            },
            singleLine = true
        )

        Button(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(50)),
            onClick = {
                if (searchString.isEmpty())
                    showError = true
                else {

                }
            }
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                text = stringResource(R.string.search_mcp_location)
            )
        }

        if (showError)
            Text(text = stringResource(R.string.please_enter_a_location_id))

    }
}