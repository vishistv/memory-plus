package com.example.vishistvarugeese.memory.Helper;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.vishistvarugeese.memory.DAO.NoteDAO;
import com.example.vishistvarugeese.memory.Variables.Entity.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class MemoryPlusDatabase extends RoomDatabase {

    private static final String DB_NAME = "MemoryPlusDatabase.db";
    private static volatile MemoryPlusDatabase instance;

    public static synchronized MemoryPlusDatabase getInstance(Context context){
        if(instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static MemoryPlusDatabase create(final Context context){
        return Room.databaseBuilder(
                context,
                MemoryPlusDatabase.class,
                DB_NAME).build();
    }

    public abstract NoteDAO getNoteDAO();
}
