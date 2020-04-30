package com.example.vishistvarugeese.memory.contacts

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.vishistvarugeese.memory.R
import kotlinx.android.synthetic.main.activity_emergency_contact.*

class EmergencyContactActivity : AppCompatActivity() {

    private var dbHelper: SQLiteHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contact)
        val prevIntent = intent
        val playAnim = prevIntent.getIntExtra("play_anim", 1)
        Log.d(TAG, playAnim.toString() + "")
        if (playAnim == 1) overridePendingTransition(R.anim.right_to_left_slide_in, R.anim.right_to_left_slide_out)

        dbHelper = SQLiteHandler(this)

//        dbHelper.deleteContacts();
        fetchContacts()
    }

    private fun fetchContacts() {
        dbHelper?.let {
            if (it.count > 0) {
                contact_list.visibility = View.VISIBLE
                no_contacts.visibility = View.GONE
                dbHelper = SQLiteHandler(this)
                contact_list.adapter = ContactsAdapter(this, it.readAllMemories(), false)
                contact_list.layoutManager = LinearLayoutManager(this)
            } else {
                no_contacts.visibility = View.VISIBLE
                contact_list.visibility = View.GONE
            }
        }
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