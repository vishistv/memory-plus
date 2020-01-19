package com.example.vishistvarugeese.memory.Repositories;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.vishistvarugeese.memory.Helper.MemoryPlusDatabase;
import com.example.vishistvarugeese.memory.Variables.Entity.Note;

import java.util.List;

public class NoteRepository {

    @SuppressLint("StaticFieldLeak")
    public void insertNote(final Note note, final Context context) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                MemoryPlusDatabase.getInstance(context).getNoteDAO().insert(note);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void updateNote(final String title, final String description, final String timestamp, final int position, final Context context) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                MemoryPlusDatabase.getInstance(context).getNoteDAO().update(title, description, timestamp, position);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteAll(final Context context) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                MemoryPlusDatabase.getInstance(context).getNoteDAO().deleteAll();
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteNote(final int id, final Context context) {

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    MemoryPlusDatabase.getInstance(context).getNoteDAO().delete(id);
                    return null;
                }
            }.execute();
    }

    public LiveData<List<Note>> getAllNotes(Context context) {
        return MemoryPlusDatabase.getInstance(context).getNoteDAO().getAllNotes();
    }




}
