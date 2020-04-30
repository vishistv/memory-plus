package com.example.vishistvarugeese.memory.contacts

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.Toast
import com.example.vishistvarugeese.memory.R
import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactsAdapter(private val mContext: Context, c: Cursor?, autoRequery: Boolean) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    private val mCursorAdapter: CursorAdapter

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = mCursorAdapter.newView(mContext, mCursorAdapter.cursor, parent)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mCursorAdapter.cursor.moveToPosition(position)
        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.cursor)

        holder.itemView.item.setOnClickListener {
            mCursorAdapter.cursor.moveToPosition(position)
            val contactDetails = ContactDetails(mCursorAdapter.cursor)
            val phoneNumber = contactDetails.getContactPhoneNumber()
            val callUri = Uri.parse("tel://$phoneNumber")
            val callIntent = Intent(Intent.ACTION_DIAL, callUri)
            callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_USER_ACTION
            mContext.startActivity(callIntent)
            Toast.makeText(mContext, contactDetails.getContactPhoneNumber(), Toast.LENGTH_SHORT).show()
        }

        holder.itemView.item.setOnLongClickListener {
            val deleteCursor = mCursorAdapter.cursor
            deleteCursor.moveToPosition(position)
            val contactDetails = ContactDetails(deleteCursor)
            val phoneNumber = contactDetails.getContactPhoneNumber()
            val name = contactDetails.getContactName()
            val dialog = AlertDialog.Builder(mContext)
            dialog.setCancelable(false)
            dialog.setTitle("$name: $phoneNumber")
            dialog.setMessage("Are you sure you want to delete this entry?")
            dialog.setPositiveButton("Delete") { _, _ ->
                val db = SQLiteHandler(mContext)
                db.deleteContact(contactDetails.getContactName())
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount)
                deleteCursor.requery()
                if (db.count == 0) {
                   (mContext as EmergencyContactActivity).noContacts()
                }
            }.setNegativeButton("Cancel ") { _, _ -> }

            val alert = dialog.create()
            alert.show()
            false
        }
    }

    override fun getItemCount(): Int {
        return mCursorAdapter.count
    }

    init {
        mCursorAdapter = object : CursorAdapter(mContext, c, autoRequery) {
            override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false)
                view.tag = ViewHolder(view)
                return view
            }

            override fun bindView(view: View, context: Context, cursor: Cursor) {
                val contactDetails = ContactDetails(cursor)
                val holder = view.tag as ViewHolder
                holder.itemView.tv_contact_name.text = contactDetails.getContactName()
                holder.itemView.tv_contact_number.text = contactDetails.getContactPhoneNumber()
                holder.itemView.tv_contact_email.text = contactDetails.getContactEmail()
                holder.itemView.iv_contact_image.setImageBitmap(contactDetails.getProfilePic())
            }
        }
    }
}