package com.example.vishistvarugeese.memory.contacts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vishistvarugeese.memory.R
import kotlinx.android.synthetic.main.activity_emergency_contact.*

class EmergencyContactActivity : AppCompatActivity() {

    private lateinit var mContactsViewModel: ContactsViewModel
    private lateinit var mContactsAdapter: ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contact)

        val prevIntent = intent
        val playAnim = prevIntent.getIntExtra("play_anim", 1)
        Log.d(TAG, playAnim.toString() + "")
        if (playAnim == 1) overridePendingTransition(R.anim.right_to_left_slide_in, R.anim.right_to_left_slide_out)

        mContactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
        mContactsAdapter = ContactsAdapter(this, mContactsViewModel)
        contact_list.adapter = mContactsAdapter
        contact_list.layoutManager = LinearLayoutManager(this)

        fetchContacts()
    }

    private fun fetchContacts() {
        mContactsViewModel.allContacts.observe(this, Observer { contacts ->
            contacts.let {
                contacts?.let {
                    if (contacts.isNotEmpty()) {
                        contact_list.visibility = View.VISIBLE
                        no_contacts.visibility = View.GONE
                        mContactsAdapter.setContacts(contacts)
                    } else {
                        no_contacts.visibility = View.VISIBLE
                        contact_list.visibility = View.GONE
                    }
                } ?: run {
                    no_contacts.visibility = View.VISIBLE
                    contact_list.visibility = View.GONE

                }
            }
        })
    }

    fun noContacts() {
        no_contacts.visibility = View.VISIBLE
        contact_list.visibility = View.GONE
    }

    fun onAddContacts(view: View?) {
        val addContactsIntent = Intent(applicationContext, EditAddContactActivity::class.java)
        addContactsIntent.putExtra("ADD", 1)
        startActivityForResult(addContactsIntent, 2000)
    }

    fun onBackPressed(view: View?) {
        finish()
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1000) {
            fetchContacts()
        }
    }

    companion object {
        private const val TAG = "EmergencyContact"
    }
}