package com.gail.mcp.repository

import com.gail.mcp.model.MCPLocationData

class MCPRepository(private val mcpDao: MCPDao) {

    suspend fun insertAll(mcpLocations: List<MCPLocationData>) {
        mcpDao.insertAll(mcpLocations)
    }

}