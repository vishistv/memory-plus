package com.example.vishistvarugeese.memory.contacts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.vishistvarugeese.memory.MemoryPlusDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactsViewModel(application: Application) : AndroidViewModel(application) {

    private val contactsRepository: ContactsRepository

    val allContacts: LiveData<List<Contacts>>

    init {
        val contactsDAO = MemoryPlusDatabase.getDatabase(application).contactsDAO()
        contactsRepository = ContactsRepository(contactsDAO)
        allContacts = contactsRepository.allContacts
    }

    fun insert(contact: Contacts) = viewModelScope.launch(Dispatchers.IO) {
        contactsRepository.insert(contact)
    }

    fun delete(contactId: Int) = viewModelScope.launch(Dispatchers.IO) {
        contactsRepository.delete(contactId)
    }

    fun update(name: String?, phoneNumber: String?, email: String?, imageAsString: String?, contactId: Int) =
            viewModelScope.launch(Dispatchers.IO) {
                contactsRepository.update(name, phoneNumber, email, imageAsString, contactId)
            }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        contactsRepository.deleteAll()
    }

}