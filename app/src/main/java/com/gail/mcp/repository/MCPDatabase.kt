package com.gail.mcp.repository

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class MCPDatabase: RoomDatabase() {

    abstract fun MCPDao(): MCPDao

    companion object {
        @Volatile
        private var INSTANCE: MCPDatabase? = null

        fun getDatabase(context: Context): MCPDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MCPDatabase::class.java,
                    "MCPDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}