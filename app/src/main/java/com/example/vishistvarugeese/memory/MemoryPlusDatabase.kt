package com.example.vishistvarugeese.memory

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.vishistvarugeese.memory.notes.NoteDAO
import com.example.vishistvarugeese.memory.notes.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class MemoryPlusDatabase : RoomDatabase() {
    abstract val noteDAO: NoteDAO

    companion object {
        private const val DB_NAME = "MemoryPlusDatabase.db"

        @Volatile
        private var instance: MemoryPlusDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MemoryPlusDatabase? {
            if (instance == null) {
                instance = create(context)
            }
            return instance
        }

        private fun create(context: Context): MemoryPlusDatabase {
            return Room.databaseBuilder(
                    context,
                    MemoryPlusDatabase::class.java,
                    DB_NAME).build()
        }
    }
}