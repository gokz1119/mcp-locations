package com.gail.mcp.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.gail.mcp.model.MCPLocationData

@Dao
interface MCPDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mcpLocations: List<MCPLocationData>)

}