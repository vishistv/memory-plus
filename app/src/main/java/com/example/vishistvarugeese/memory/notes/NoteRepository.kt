package com.example.vishistvarugeese.memory.notes

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import android.content.Context
import android.os.AsyncTask
import com.example.vishistvarugeese.memory.MemoryPlusDatabase

class NoteRepository {
    @SuppressLint("StaticFieldLeak")
    fun insertNote(note: Note?, context: Context) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                MemoryPlusDatabase.getDatabase(context).noteDAO.insert(note)
                return null
            }
        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    fun updateNote(title: String?, description: String?, timestamp: String?, position: Int, context: Context) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                MemoryPlusDatabase.getDatabase(context).noteDAO.update(title, description, timestamp, position)
                return null
            }
        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    fun deleteAll(context: Context) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                MemoryPlusDatabase.getDatabase(context).noteDAO.deleteAll()
                return null
            }
        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    fun deleteNote(id: Int, context: Context) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                MemoryPlusDatabase.getDatabase(context).noteDAO.delete(id)
                return null
            }
        }.execute()
    }

    fun getAllNotes(context: Context): LiveData<List<Note>>? {
        return MemoryPlusDatabase.getDatabase(context).noteDAO.allNotes
    }
}