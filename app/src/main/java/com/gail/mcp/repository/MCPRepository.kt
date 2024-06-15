package com.gail.mcp.repository

import android.content.Context
import android.net.Uri
import com.gail.mcp.model.MCPLocationData
import java.io.BufferedReader
import java.io.InputStreamReader

class MCPRepository(
    context: Context
) {
    val mcpDao = MCPDatabase.getDatabase(context).MCPDao()

    suspend fun insertAll(mcpLocations: List<MCPLocationData>) {
        mcpDao.insertAll(mcpLocations)
    }

    suspend fun getAllLocations(): List<MCPLocationData> {
        return mcpDao.getAllLocations()
    }

    suspend fun getCount(): Int {
        return mcpDao.getCount()
    }

    suspend fun searchLocation(searchString: String): MCPLocationData? {
        return mcpDao.searchLocation(searchString)
    }

    fun readCsvFromUri(context: Context, uri: Uri): List<MCPLocationData> {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))

        val mcpLocations = mutableListOf<MCPLocationData>()
        bufferedReader.use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val tokens = line?.split(",")
                if (tokens != null && tokens.size >= 2) {
                    val id = tokens[0].trim()
                    val name = tokens[1].trim()
                    var imageUrl = ""
                    if (tokens.size == 3) {
                        val imageUrlFromCsv = tokens[2].trim()
                        if (imageUrlFromCsv.startsWith("https://drive.google.com/file/d/")) {
                            val startIndex = imageUrlFromCsv.indexOf("/d/") + 3
                            val endIndex = imageUrlFromCsv.indexOf("/", startIndex)
                            val fileId = imageUrlFromCsv.substring(startIndex, endIndex)
                            imageUrl = "https://drive.google.com/uc?id=$fileId"
                        }
                    }
                    mcpLocations.add(MCPLocationData(id, name, imageUrl))
                }
            }
        }

        return mcpLocations
    }

}