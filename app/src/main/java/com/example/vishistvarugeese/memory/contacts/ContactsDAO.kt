package com.example.vishistvarugeese.memory.contacts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vishistvarugeese.memory.notes.Note

@Dao
interface ContactsDAO {

    @Insert
    fun insertContacts(contacts: Contacts)

    @get:Query("SELECT * FROM Contacts")
    val getAllContacts: LiveData<List<Contacts>>

    @Query("DELETE FROM Contacts WHERE id=:contactId")
    fun deleteContact(contactId: Int)

    @Query("DELETE FROM Contacts")
    fun deleteAllContacts()

    @Query("UPDATE Contacts SET name =:name, phoneNumber =:phoneNumber, email =:email, imageAsString=:imageAsString WHERE id=:contactId")
    fun updateContacts(name: String?, phoneNumber: String?, email: String?, imageAsString: String?, contactId: Int)
}