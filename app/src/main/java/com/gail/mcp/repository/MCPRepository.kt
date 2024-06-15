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

    fun readCsvFromUri(context: Context, uri: Uri): List<MCPLocationData> {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))

        val mcpLocations = mutableListOf<MCPLocationData>()
        bufferedReader.use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val tokens = line?.split(",")
                if (tokens != null && tokens.size == 2) {
                    val id = tokens[0].trim()
                    val name = tokens[1].trim()
                    mcpLocations.add(MCPLocationData(id, name))
                }
            }
        }

        return mcpLocations
    }

}