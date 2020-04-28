package com.example.vishistvarugeese.memory.Adapters

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
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.vishistvarugeese.memory.EmergencyContactActivity
import com.example.vishistvarugeese.memory.Helper.SQLiteHandler
import com.example.vishistvarugeese.memory.R
import com.example.vishistvarugeese.memory.Variables.Contact_details
import de.hdodenhof.circleimageview.CircleImageView

class ContactsAdapter(private val mContext: Context, c: Cursor?, autoRequery: Boolean) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    private val mCursorAdapter: CursorAdapter

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_contactName: TextView
        val tv_contactPhoneNumber: TextView
        val tv_contactEmail: TextView
        val iv_profilePic: CircleImageView
        val ll_item: LinearLayout

        init {
            tv_contactName = itemView.findViewById<View>(R.id.tv_contact_name) as TextView
            tv_contactPhoneNumber = itemView.findViewById<View>(R.id.tv_contact_number) as TextView
            tv_contactEmail = itemView.findViewById<View>(R.id.tv_contact_email) as TextView
            iv_profilePic = itemView.findViewById<View>(R.id.iv_contact_image) as CircleImageView
            ll_item = itemView.findViewById<View>(R.id.item) as LinearLayout
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = mCursorAdapter.newView(mContext, mCursorAdapter.cursor, parent)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mCursorAdapter.cursor.moveToPosition(position)
        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.cursor)
        holder.ll_item.setOnClickListener {
            mCursorAdapter.cursor.moveToPosition(position)
            val contact_details = Contact_details(mCursorAdapter.cursor)
            val phoneNumber = contact_details.contactPhoneNumber
            val callUri = Uri.parse("tel://$phoneNumber")
            val callIntent = Intent(Intent.ACTION_DIAL, callUri)
            callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_USER_ACTION
            mContext.startActivity(callIntent)
            Toast.makeText(mContext, contact_details.contactPhoneNumber, Toast.LENGTH_SHORT).show()
        }
        holder.ll_item.setOnLongClickListener {
            val deleteCursor = mCursorAdapter.cursor
            deleteCursor.moveToPosition(position)
            val contact_details = Contact_details(deleteCursor)
            val phoneNumber = contact_details.contactPhoneNumber
            val name = contact_details.contactName
            val dialog = AlertDialog.Builder(mContext)
            dialog.setCancelable(false)
            dialog.setTitle("$name: $phoneNumber")
            dialog.setMessage("Are you sure you want to delete this entry?")
            dialog.setPositiveButton("Delete") { dialog, id ->
                val db = SQLiteHandler(mContext)
                db.deleteContact(contact_details.contactName)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount)
                deleteCursor.requery()
                if (db.count == 0) {
                    EmergencyContactActivity.Companion.noContacts()
                }
            }
                    .setNegativeButton("Cancel ") { dialog, which -> }
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
                val contact_details = Contact_details(cursor)
                val holder = view.tag as ViewHolder
                holder.tv_contactName.text = contact_details.contactName
                holder.tv_contactPhoneNumber.text = contact_details.contactPhoneNumber
                holder.tv_contactEmail.text = contact_details.contactEmail
                holder.iv_profilePic.setImageBitmap(contact_details.profilePic)
            }
        }
    }
}