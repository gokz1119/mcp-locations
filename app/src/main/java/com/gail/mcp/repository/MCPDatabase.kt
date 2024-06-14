package com.gail.mcp.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gail.mcp.model.MCPLocationData

@Database(entities = [MCPLocationData::class], version = 1)
abstract class MCPDatabase: RoomDatabase() {

    abstract fun MCPDao(): MCPDao

    companion object {
        @Volatile
        private var INSTANCE: MCPDatabase? = null

        fun getDatabase(context: Context): MCPDatabase {
            return INSTANCE ?: createDB(context).also {
                INSTANCE = it
            }
        }

        private fun createDB(context: Context): MCPDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MCPDatabase::class.java,
                "MCPDatabase"
            ).build()
        }
    }
}