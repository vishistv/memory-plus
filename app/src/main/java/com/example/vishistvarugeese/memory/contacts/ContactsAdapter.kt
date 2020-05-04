package com.example.vishistvarugeese.memory.contacts

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.vishistvarugeese.memory.R
import kotlinx.android.synthetic.main.contact_list_item.view.*


class ContactsAdapter internal constructor(
        private val mContext: Context,
        private val mContactsViewModel: ContactsViewModel
) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    private var contacts = emptyList<Contacts>()

    inner class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

            itemView.setOnClickListener {
                val position = adapterPosition
                val phoneNumber = contacts[position].phoneNumber
                val callUri = Uri.parse("tel://$phoneNumber")
                val callIntent = Intent(Intent.ACTION_DIAL, callUri)
                callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_USER_ACTION
                mContext.startActivity(callIntent)
                Toast.makeText(mContext, phoneNumber, Toast.LENGTH_SHORT).show()
            }

            itemView.setOnLongClickListener {
                val position = adapterPosition

                val contactDetails = contacts[position]
                val phoneNumber = contactDetails.phoneNumber
                val name = contactDetails.name
                val id = contactDetails.id

                val dialog = AlertDialog.Builder(mContext)

                dialog.setCancelable(false)
                dialog.setTitle("$name: $phoneNumber")
                dialog.setMessage("Are you sure you want to delete this entry?")
                dialog.setPositiveButton("Delete") { _, _ ->
                    mContactsViewModel.delete(id)
                }.setNegativeButton("Cancel ") { _, _ -> }

                val alert = dialog.create()
                alert.show()
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.contact_list_item, parent, false)
        return ContactsViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {

        val contactDetails = contacts[position]
        holder.itemView.tv_contact_name.text = contactDetails.name
        holder.itemView.tv_contact_number.text = contactDetails.phoneNumber
        holder.itemView.tv_contact_email.text = contactDetails.email
        holder.itemView.iv_contact_image.setImageBitmap(
                ContactsHelper.stringToBitmap(
                        contactDetails.imageAsString
                ))

//        holder.itemView.item.setOnClickListener {
//            val phoneNumber = contactDetails.phoneNumber
//            val callUri = Uri.parse("tel://$phoneNumber")
//            val callIntent = Intent(Intent.ACTION_DIAL, callUri)
//            callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_USER_ACTION
//            mContext.startActivity(callIntent)
//            Toast.makeText(mContext, contactDetails.phoneNumber, Toast.LENGTH_SHORT).show()
//        }
//
//        holder.itemView.item.setOnLongClickListener {
//            val phoneNumber = contactDetails.phoneNumber
//            val name = contactDetails.name
//            val dialog = AlertDialog.Builder(mContext)
//            dialog.setCancelable(false)
//            dialog.setTitle("$name: $phoneNumber")
//            dialog.setMessage("Are you sure you want to delete this entry?")
//            dialog.setPositiveButton("Delete") { _, _ ->
//                val db = SQLiteHandler(mContext)
//                db.deleteContact(contactDetails.name)
//                notifyItemRemoved(position)
//                notifyItemRangeChanged(position, itemCount)
//                if (db.count == 0) {
//                   (mContext as EmergencyContactActivity).noContacts()
//                }
//            }.setNegativeButton("Cancel ") { _, _ -> }
//
//            val alert = dialog.create()
//            alert.show()
//            false
//        }
    }

    internal fun setContacts(contacts: List<Contacts>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}