package com.example.vishistvarugeese.memory.notes

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface NoteDAO {
    @get:Query("SELECT * FROM Note")
    val allNotes: LiveData<List<Note>>

    @Query("SELECT * FROM Note WHERE id=:note_id")
    fun getNote(note_id: Int): Note?

    @Query("DELETE FROM Note")
    fun deleteAll()

    @Insert
    fun insert(note: Note?)

    @Query("DELETE FROM Note WHERE id=:note_id")
    fun delete(note_id: Int)

    @Query("UPDATE Note SET title =:title, description =:description, timestamp =:timestamp WHERE id=:note_id")
    fun update(title: String?, description: String?, timestamp: String?, note_id: Int)
}