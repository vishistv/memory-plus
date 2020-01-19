package com.example.vishistvarugeese.memory.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.vishistvarugeese.memory.Variables.Entity.Note;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NoteDAO {

    @Query("SELECT * FROM Note")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM Note WHERE id=:note_id")
    Note getNote(int note_id);

    @Query("DELETE FROM Note")
    void deleteAll();

    @Insert
    void insert(Note note);

    @Query("DELETE FROM Note WHERE id=:note_id")
    void delete(int note_id);

    @Query("UPDATE Note SET title =:title, description =:description, timestamp =:timestamp WHERE id=:note_id")
    void update(String title, String description, String timestamp, int note_id);
}
