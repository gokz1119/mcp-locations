package com.gail.mcp.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gail.mcp.R
import com.gail.mcp.viewmodel.MCPViewModel

@Composable
fun FilePickerView(
    viewModel: MCPViewModel
) {
    val context = LocalContext.current
    val result = remember {
        mutableStateOf<Uri?>(null)
    }
    var csvContent by remember { mutableStateOf<List<String>?>(null) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) {
        result.value = it
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp),
            text = stringResource(R.string.select_a_csv_file),
            fontSize = 16.sp
        )
        Button(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(50)),
            onClick = {
                launcher.launch(arrayOf("text/csv", "text/comma-separated-values"))
            }
        ) {
            Text(
                text = stringResource(R.string.select_file),
                fontSize = 16.sp
            )
        }

        result.value?.let { uri ->
            LaunchedEffect(key1 = uri) {
                viewModel.loadMcpDataFromCSV(context, uri)
            }
        }

        csvContent?.let {
            for(i in 0..5) {
                Text(text = it[i])
            }
        }
    }
}