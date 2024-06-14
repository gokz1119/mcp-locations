package com.gail.mcp.ui

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.navigation.NavHostController
import com.gail.mcp.R
import com.gail.mcp.viewmodel.MCPViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.io.BufferedReader
import java.io.InputStreamReader

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun FilePickerView(
    viewModel: MCPViewModel,
    navController: NavHostController
) {
    val readStoragePermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO
        )
    )
    val context = LocalContext.current
    val result = remember {
        mutableStateOf<Uri?>(null)
    }
    var csvContent by remember { mutableStateOf<List<String>?>(null) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
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
                if (readStoragePermissionState.allPermissionsGranted)
                    launcher.launch("*/*")
                else
                    readStoragePermissionState.launchMultiplePermissionRequest()
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

fun readCsvFromUri(context: Context, uri: Uri): List<String> {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))

    val lines = mutableListOf<String>()
    bufferedReader.use { reader ->
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            lines.add(line!!)
        }
    }

    return lines
}