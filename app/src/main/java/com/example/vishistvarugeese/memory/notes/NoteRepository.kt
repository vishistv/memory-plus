package com.example.vishistvarugeese.memory.notes

import android.content.Context
import androidx.lifecycle.LiveData

class NoteRepository(private val noteDAO: NoteDAO) {

    val allNotes: LiveData<List<Note>> = noteDAO.allNotes

    suspend fun insertNote(note: Note?) {
        noteDAO.insert(note)
    }

    suspend fun updateNote(title: String?, description: String?, timestamp: String?, position: Int) {
        noteDAO.update(title, description, timestamp, position)
    }

    suspend fun deleteAll() {
        noteDAO.deleteAll()
    }

    suspend fun deleteNote(id: Int) {
        noteDAO.delete(id)
    }

}