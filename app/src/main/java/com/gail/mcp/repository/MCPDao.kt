package com.gail.mcp.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gail.mcp.model.MCPLocationData

@Dao
interface MCPDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mcpLocations: List<MCPLocationData>)

    @Query("SELECT * FROM mcp_location")
    suspend fun getAllLocations(): List<MCPLocationData>

    @Query("SELECT COUNT(*) FROM mcp_location")
    suspend fun getCount(): Int

    @Query("SELECT * FROM mcp_location WHERE LOWER(locationId) = LOWER(:id)")
    suspend fun searchLocation(id: String): MCPLocationData?

}