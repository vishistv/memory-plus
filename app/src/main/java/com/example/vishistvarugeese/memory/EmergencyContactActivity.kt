package com.example.vishistvarugeese.memory

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.example.vishistvarugeese.memory.Adapters.ContactsAdapter
import com.example.vishistvarugeese.memory.Helper.SQLiteHandler

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
        recyclerView = findViewById<View>(R.id.contact_list) as RecyclerView
        ll_noContacts = findViewById<View>(R.id.no_contacts) as LinearLayout

//        dbHelper.deleteContacts();
        if (dbHelper.getCount() > 0) {
            recyclerView!!.visibility = View.VISIBLE
            ll_noContacts!!.visibility = View.GONE
            dbHelper = SQLiteHandler(this)
            recyclerView.setAdapter(ContactsAdapter(this, dbHelper!!.readAllMemories(), false))
            recyclerView.setLayoutManager(LinearLayoutManager(this))
        } else {
            ll_noContacts!!.visibility = View.VISIBLE
            recyclerView!!.visibility = View.GONE
        }
    }

    fun onAddContacts(view: View?) {
        val addContactsIntent = Intent(applicationContext, EditAdd_ContactActivity::class.java)
        addContactsIntent.putExtra("ADD", 1)
        startActivity(addContactsIntent)
        //        finish();
    }

    fun onBackPressed(view: View?) {
//        startActivity(new Intent(this, DashboardActivity.class));
        finish()
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //        startActivity(new Intent(this, DashboardActivity.class));
        finish()
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out)
    }

    companion object {
        private const val TAG = "EmergencyContact"
        private var recyclerView: RecyclerView? = null
        private var ll_noContacts: LinearLayout? = null
        fun noContacts() {
            ll_noContacts!!.visibility = View.VISIBLE
            recyclerView!!.visibility = View.GONE
        }
    }
}