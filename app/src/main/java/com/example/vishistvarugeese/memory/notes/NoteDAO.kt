package com.example.vishistvarugeese.memory.notes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDAO {
    @get:Query("SELECT * FROM Note")
    val allNotes: LiveData<List<Note>>

    @Query("SELECT * FROM Note WHERE id=:noteId")
    fun getNote(noteId: Int): Note?

    @Query("DELETE FROM Note")
    fun deleteAll()

    @Insert
    fun insert(note: Note?)

    @Query("DELETE FROM Note WHERE id=:noteId")
    fun delete(noteId: Int)

    @Query("UPDATE Note SET title =:title, description =:description, timestamp =:timestamp WHERE id=:noteId")
    fun update(title: String?, description: String?, timestamp: String?, noteId: Int)
}