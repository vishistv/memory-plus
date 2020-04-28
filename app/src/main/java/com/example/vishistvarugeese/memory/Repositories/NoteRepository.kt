package com.example.vishistvarugeese.memory.Repositories

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.AsyncTask
import com.example.vishistvarugeese.memory.Helper.MemoryPlusDatabase
import com.example.vishistvarugeese.memory.Variables.Entity.Note

class NoteRepository {
    @SuppressLint("StaticFieldLeak")
    fun insertNote(note: Note?, context: Context) {
        object : AsyncTask<Void?, Void?, Void?>() {
            protected override fun doInBackground(vararg voids: Void): Void? {
                MemoryPlusDatabase.Companion.getInstance(context).getNoteDAO().insert(note)
                return null
            }
        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    fun updateNote(title: String?, description: String?, timestamp: String?, position: Int, context: Context) {
        object : AsyncTask<Void?, Void?, Void?>() {
            protected override fun doInBackground(vararg voids: Void): Void? {
                MemoryPlusDatabase.Companion.getInstance(context).getNoteDAO().update(title, description, timestamp, position)
                return null
            }
        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    fun deleteAll(context: Context) {
        object : AsyncTask<Void?, Void?, Void?>() {
            protected override fun doInBackground(vararg voids: Void): Void? {
                MemoryPlusDatabase.Companion.getInstance(context).getNoteDAO().deleteAll()
                return null
            }
        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    fun deleteNote(id: Int, context: Context) {
        object : AsyncTask<Void?, Void?, Void?>() {
            protected override fun doInBackground(vararg voids: Void): Void? {
                MemoryPlusDatabase.Companion.getInstance(context).getNoteDAO().delete(id)
                return null
            }
        }.execute()
    }

    fun getAllNotes(context: Context): LiveData<List<Note>> {
        return MemoryPlusDatabase.Companion.getInstance(context).getNoteDAO().getAllNotes()
    }
}