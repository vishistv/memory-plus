package com.example.vishistvarugeese.memory.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.vishistvarugeese.memory.R
import com.example.vishistvarugeese.memory.Variables.Entity.Note
import java.util.*

class NotesAdapter(onNoteListener: OnNoteListener?, notesList: ArrayList<Note>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    private val notesList = ArrayList<Note>()
    private val onNoteListener: OnNoteListener?
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.note_list_item, parent, false)
        return ViewHolder(v, onNoteListener!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteItem = notesList[position]
        holder.tv_noteDescription.text = noteItem.description
        holder.tv_noteTitle.text = noteItem.title
        holder.tv_noteTimestamp.text = noteItem.timestamp
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder(itemView: View, onNoteListener: OnNoteListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val tv_noteTitle: TextView
        val tv_noteDescription: TextView
        val tv_noteTimestamp: TextView
        var mOnNoteListener: OnNoteListener
        override fun onClick(v: View) {
            onNoteListener!!.onNoteClick(adapterPosition)
        }

        init {
            tv_noteTitle = itemView.findViewById<View>(R.id.tv_note_title) as TextView
            tv_noteDescription = itemView.findViewById<View>(R.id.tv_note_dialog_description) as TextView
            tv_noteTimestamp = itemView.findViewById<View>(R.id.tv_note_timestamp) as TextView
            mOnNoteListener = onNoteListener
            itemView.setOnClickListener(this)
        }
    }

    interface OnNoteListener {
        fun onNoteClick(position: Int)
    }

    init {
        this.notesList = notesList
        this.onNoteListener = onNoteListener
    }
}