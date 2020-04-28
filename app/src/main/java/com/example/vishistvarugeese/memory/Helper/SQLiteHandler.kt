package com.example.vishistvarugeese.memory.Helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.vishistvarugeese.memory.Variables.Contact_Contract.ContactEntry
import com.example.vishistvarugeese.memory.Variables.Contact_details

/**
 * Created by Vishist Varugeese on 28-12-2017.
 */
class SQLiteHandler(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // Creating Tables
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_LOGIN_TABLE = "CREATE TABLE " + ContactEntry.TABLE_NAME + "(" +
                BaseColumns._ID + " INTEGER PRIMARY KEY," +
                ContactEntry.COLUMN_NAME + " TEXT," +
                ContactEntry.COLUMN_PHONE_NUMBER + " TEXT," +
                ContactEntry.COLUMN_EMAIL + " TEXT," +
                ContactEntry.COLUMN_PHOTO + " TEXT" + " );"
        db.execSQL(CREATE_LOGIN_TABLE)
        Log.d(TAG, "Database tables created")
    }

    // Upgrading database
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ContactEntry.TABLE_NAME)

        // Create tables again
        onCreate(db)
    }

    /**
     * Storing contact details in database
     */
    fun addContacts(contact_details: Contact_details) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(ContactEntry.COLUMN_NAME, contact_details.contactName) // Name
        values.put(ContactEntry.COLUMN_PHONE_NUMBER, contact_details.contactPhoneNumber) // Phone Number
        values.put(ContactEntry.COLUMN_EMAIL, contact_details.contactEmail) // Email
        values.put(ContactEntry.COLUMN_PHOTO, contact_details.imageAsString) //photo

        // Inserting Row
        val id = db.insert(ContactEntry.TABLE_NAME, null, values)
        db.close() // Closing database connection
        Log.d(TAG, "New contact inserted into sqlite: $id")
    }

    val count: Int
        get() {
            Log.d("hellll", "Count: ")
            val selectQuery = "SELECT * FROM " + ContactEntry.TABLE_NAME
            val db = this.readableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            return cursor.count
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
    fun readAllMemories(): Cursor {
        val db = readableDatabase
        return db.query(
                ContactEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        )
    }

    /**
     * Recreate database Delete all tables and create them again
     */
    fun deleteAllContacts() {
        val db = this.writableDatabase
        // Delete All Rows
        db.delete(ContactEntry.TABLE_NAME, null, null)
        db.close()
        Log.d(TAG, "Deleted all contact info from sqlite")
    }

    fun deleteContact(name: String?) {
        val db = this.writableDatabase
        db.delete(ContactEntry.TABLE_NAME, ContactEntry.COLUMN_NAME + "=?", arrayOf(name))
        db.close()
        Log.d(TAG, "Deleted $name")
    }

    companion object {
        private val TAG = SQLiteHandler::class.java.simpleName

        // All Static variables
        // Database Version
        private const val DATABASE_VERSION = 3

        // Database Name
        private const val DATABASE_NAME = "memory_plus"
    }
}