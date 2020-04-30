package com.example.vishistvarugeese.memory.notes

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.AsyncTask
import com.example.vishistvarugeese.memory.MemoryPlusDatabase

class NoteRepository {
    @SuppressLint("StaticFieldLeak")
    fun insertNote(note: Note?, context: Context) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                MemoryPlusDatabase.getInstance(context)?.noteDAO?.insert(note)
                return null
            }
        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    fun updateNote(title: String?, description: String?, timestamp: String?, position: Int, context: Context) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                MemoryPlusDatabase.getInstance(context)?.noteDAO?.update(title, description, timestamp, position)
                return null
            }
        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    fun deleteAll(context: Context) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                MemoryPlusDatabase.getInstance(context)?.noteDAO?.deleteAll()
                return null
            }
        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    fun deleteNote(id: Int, context: Context) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                MemoryPlusDatabase.getInstance(context)?.noteDAO?.delete(id)
                return null
            }
        }.execute()
    }

    fun getAllNotes(context: Context): LiveData<List<Note>>? {
        return MemoryPlusDatabase.getInstance(context)?.noteDAO?.allNotes
    }
}