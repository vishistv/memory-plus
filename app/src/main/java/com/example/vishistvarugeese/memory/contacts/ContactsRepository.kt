package com.example.vishistvarugeese.memory.contacts

import androidx.lifecycle.LiveData

class ContactsRepository(private val contactsDAO: ContactsDAO) {

    val allContacts: LiveData<List<Contacts>> = contactsDAO.getAllContacts

    suspend fun insert(contact: Contacts) {
        contactsDAO.insertContacts(contact)
    }

    suspend fun delete(contactId: Int) {
        contactsDAO.deleteContact(contactId)
    }

    suspend fun deleteAll() {
        contactsDAO.deleteAllContacts()
    }

    suspend fun update(name: String?, phoneNumber: String?, email: String?, imageAsString: String?, contactId: Int) {
        contactsDAO.updateContacts(name, phoneNumber, email, imageAsString, contactId)
    }


}