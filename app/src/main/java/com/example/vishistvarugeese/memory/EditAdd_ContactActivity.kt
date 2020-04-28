package com.example.vishistvarugeese.memory

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.vishistvarugeese.memory.Helper.SQLiteHandler
import com.example.vishistvarugeese.memory.Variables.Contact_details
import java.io.IOException

class EditAdd_ContactActivity : AppCompatActivity() {
    private var iv_add_contact_image: ImageView? = null
    private var tv_toolbar_contact_title: TextView? = null
    private var et_add_contact_name: EditText? = null
    private var et_add_contact_phoneNumber: EditText? = null
    private var et_add_contact_email: EditText? = null
    private var btn_edit_add_contact: Button? = null
    private var btn_delete_contact: Button? = null
    private var contact_name: String? = null
    private var contact_phoneNumber: String? = null
    private var contact_email: String? = null
    private val contact_pic: String? = null
    var picturePath: String? = null
    private val imageSet = false
    private val default_contact_pic = intArrayOf(R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four)
    private val KEY_CONTACT_PIC = "defaultContactPics"
    private val PREF_NAME = "memoryPlus"
    private val PRIVATE_MODE = 0
    private val GALLERY_REQUEST_CODE = 1
    var pref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    private var db: SQLiteHandler? = null
    var i: Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_add__contact)
        overridePendingTransition(R.anim.right_to_left_slide_in, R.anim.right_to_left_slide_out)
        val intent = intent
        val i = intent.getIntExtra("ADD", 0)
        pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
        tv_toolbar_contact_title = findViewById<View>(R.id.toolbar_contact_title) as TextView
        iv_add_contact_image = findViewById<View>(R.id.iv_contact_set_image) as ImageView
        et_add_contact_name = findViewById<View>(R.id.et_contact_set_name) as EditText
        et_add_contact_phoneNumber = findViewById<View>(R.id.et_contact_set_phoneNumber) as EditText
        et_add_contact_email = findViewById<View>(R.id.et_contact_set_email) as EditText
        btn_edit_add_contact = findViewById<View>(R.id.btn_edit_add_contact) as Button
        btn_delete_contact = findViewById<View>(R.id.btn_delete_contact) as Button
        db = SQLiteHandler(applicationContext)

        //ONLY FOR TESTING
//        et_add_contact_name.setText("Rahul Varma");
//        et_add_contact_phoneNumber.setText("09442799642");
//        et_add_contact_email.setText("test@gmail.com");
        if (i == 1) {
            tv_toolbar_contact_title!!.text = "A D D  C O N T A C T"
            btn_edit_add_contact!!.text = "S A V E  C O N T A C T"
        } else {
            tv_toolbar_contact_title!!.text = "E D I T  C O N T A C T"
            btn_edit_add_contact!!.text = "S A V E  C H A N G E S"
            btn_delete_contact!!.visibility = View.VISIBLE
        }
    }

    fun onEditAddContact(view: View?) {
        contact_name = et_add_contact_name!!.text.toString()
        contact_phoneNumber = et_add_contact_phoneNumber!!.text.toString()
        contact_email = et_add_contact_email!!.text.toString()
        val image = (iv_add_contact_image!!.drawable as BitmapDrawable).bitmap
        SQLiteHandler(this).addContacts(Contact_details(contact_name!!, contact_phoneNumber!!, contact_email!!, image))
        val contactsIntent = Intent(applicationContext, EmergencyContactActivity::class.java)
        startActivity(contactsIntent)
        finish()
    }

    fun onAddImageClick(view: View?) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            try {
                val selectedImage = data.data
                val imageStream = contentResolver.openInputStream(selectedImage)
                iv_add_contact_image!!.setImageBitmap(BitmapFactory.decodeStream(imageStream))
                iv_add_contact_image!!.tag = "image_set"
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
    }

    fun onDeleteContacts(view: View?) {}
    fun onBackPressed(view: View?) {
//        Intent backIntent = new Intent(this, EmergencyContactActivity.class);
//        backIntent.putExtra("play_anim", 3);
//        startActivity(backIntent);
        finish()
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //        Intent backIntent = new Intent(this, EmergencyContactActivity.class);
//        backIntent.putExtra("play_anim", 3);
//        startActivity(backIntent);
        finish()
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out)
    }

    companion object {
        private const val PREFERRED_WIDTH = 60f
        private const val PREFERRED_HEIGHT = 60f
    }
}