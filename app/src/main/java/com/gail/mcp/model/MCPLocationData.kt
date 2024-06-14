package com.gail.mcp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mcp_location")
data class MCPLocationData(
    @PrimaryKey val locationId: String = "",
    val locationName: String = ""
)
