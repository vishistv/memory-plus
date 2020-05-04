package com.example.vishistvarugeese.memory.contacts

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.vishistvarugeese.memory.R
import kotlinx.android.synthetic.main.activity_edit_add__contact.*
import kotlinx.coroutines.selects.select
import java.io.IOException

class EditAddContactActivity : AppCompatActivity() {

    private val GALLERY_REQUEST_CODE = 1
    private lateinit var mContactsViewModel: ContactsViewModel

    var i: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_add__contact)
        overridePendingTransition(R.anim.right_to_left_slide_in, R.anim.right_to_left_slide_out)
        val intent = intent
        val i = intent.getIntExtra("ADD", 0)
        mContactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)


        //ONLY FOR TESTING
//        et_contact_set_name.setText("Rahul Varma");
//        et_contact_set_phoneNumber.setText("09442799642");
//        et_contact_set_email.setText("test@gmail.com");

        if (i == 1) {
            toolbar_contact_title?.text = "A D D  C O N T A C T"
            btn_edit_add_contact?.text = "S A V E  C O N T A C T"
        } else {
            toolbar_contact_title!!.text = "E D I T  C O N T A C T"
            btn_edit_add_contact?.text = "S A V E  C H A N G E S"
            btn_delete_contact?.visibility = View.VISIBLE
        }
    }

    fun onEditAddContact(view: View?) {
        val contactName = et_contact_set_name?.text.toString()
        val contactPhonenumber = et_contact_set_phoneNumber?.text.toString()
        val contactEmail = et_contact_set_email?.text.toString()
        val image = (iv_contact_set_image?.drawable as BitmapDrawable).bitmap
        mContactsViewModel.insert(Contacts(contactName, contactPhonenumber, contactEmail, ContactsHelper.bitmapToString(image)))
        setResult(1000)
        onBackPressed()
    }

    fun onAddImageClick(view: View?) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE && data != null) {
            try {
                data.data?.let {
                    val selectedImage: Uri = it
                    val imageStream = contentResolver.openInputStream(selectedImage)
                    iv_contact_set_image?.setImageBitmap(BitmapFactory.decodeStream(imageStream))
                    iv_contact_set_image?.tag = "image_set"
                }
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
    }

    fun onDeleteContacts() {}

    fun onBackButtonPressed(view: View?) {
        finish()
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out)
    }
}