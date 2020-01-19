package com.example.vishistvarugeese.memory.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.vishistvarugeese.memory.Variables.Contact_Contract;
import com.example.vishistvarugeese.memory.Variables.Contact_details;

/**
 * Created by Vishist Varugeese on 28-12-2017.
 */

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "memory_plus";



    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + Contact_Contract.ContactEntry.TABLE_NAME + "(" +
                Contact_Contract.ContactEntry._ID + " INTEGER PRIMARY KEY," +
                Contact_Contract.ContactEntry.COLUMN_NAME + " TEXT," +
                Contact_Contract.ContactEntry.COLUMN_PHONE_NUMBER + " TEXT," +
                Contact_Contract.ContactEntry.COLUMN_EMAIL + " TEXT," +
                Contact_Contract.ContactEntry.COLUMN_PHOTO + " TEXT" + " );";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Contact_Contract.ContactEntry.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing contact details in database
     * */
    public void addContacts(Contact_details contact_details) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contact_Contract.ContactEntry.COLUMN_NAME, contact_details.getContactName()); // Name
        values.put(Contact_Contract.ContactEntry.COLUMN_PHONE_NUMBER, contact_details.getContactPhoneNumber()); // Phone Number
        values.put(Contact_Contract.ContactEntry.COLUMN_EMAIL, contact_details.getContactEmail()); // Email
        values.put(Contact_Contract.ContactEntry.COLUMN_PHOTO, contact_details.getImageAsString()); //photo

        // Inserting Row
        long id = db.insert(Contact_Contract.ContactEntry.TABLE_NAME, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New contact inserted into sqlite: " + id);
    }

    public int getCount(){
        Log.d("hellll", "Count: " );

        String selectQuery = "SELECT * FROM " + Contact_Contract.ContactEntry.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount();
    }

//    /**
//     * Getting contact data from database
//     * */
//    public List<Contact_details> getContactDetails() {
//        List<Contact_details> contactList = new ArrayList<>();
//        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        // Move to first row
//        cursor.moveToFirst();
//        while (cursor.getCount() > 0 && !(cursor.isAfterLast())) {
//            Contact_details contact_item = new Contact_details(
//                    cursor.getString(1),
//                    cursor.getString(2),
//                    cursor.getString(3),
//                    cursor.getString(4)
//            );
//
//            contactList.add(contact_item);
//        }
//        cursor.close();
//        db.close();
//        // return user
//        Log.d(TAG, "Fetching contact from Sqlite: " + contactList.toString());
//
//        return contactList;
//    }

    public Cursor readAllMemories() {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(
                Contact_Contract.ContactEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    /**
     * Recreate database Delete all tables and create them again
     * */
    public void deleteAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(Contact_Contract.ContactEntry.TABLE_NAME, null, null);
        db.close();

        Log.d(TAG, "Deleted all contact info from sqlite");
    }

    public void deleteContact(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Contact_Contract.ContactEntry.TABLE_NAME, Contact_Contract.ContactEntry.COLUMN_NAME + "=?", new String[]{name});
        db.close();

        Log.d(TAG, "Deleted " + name);

    }

}
