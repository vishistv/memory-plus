package com.example.vishistvarugeese.memory.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vishistvarugeese.memory.MemoryPlusDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository: NoteRepository

    val allNote: LiveData<List<Note>>

    init {
        val noteDAO = MemoryPlusDatabase.getDatabase(application).noteDAO
        noteRepository = NoteRepository(noteDAO)
        allNote = noteRepository.allNotes
    }

    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.insertNote(note)
    }

    fun update(title: String?, description: String?, timestamp: String?, position: Int) =
            viewModelScope.launch(Dispatchers.IO) {
        noteRepository.updateNote(title, description, timestamp, position)
    }

    fun delete(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteNote(id)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteAll()
    }
}