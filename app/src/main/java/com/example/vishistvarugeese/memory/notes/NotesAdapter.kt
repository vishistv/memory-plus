package com.example.vishistvarugeese.memory.notes

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vishistvarugeese.memory.R
import kotlinx.android.synthetic.main.note_list_item.view.*
import java.util.*

class NotesAdapter(onNoteListener: OnNoteListener?, notesList: ArrayList<Note>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    private var mNotesList = ArrayList<Note>()
    private val mOnNoteListener: OnNoteListener?

    init {
        this.mNotesList = notesList
        this.mOnNoteListener = onNoteListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.note_list_item, parent, false)
        return ViewHolder(v, mOnNoteListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteItem = mNotesList[position]
        holder.itemView.tv_note_dialog_description.text = noteItem.description
        holder.itemView.tv_note_title.text = noteItem.title
        holder.itemView.tv_note_timestamp.text = noteItem.timestamp
    }

    override fun getItemCount(): Int {
        return mNotesList.size
    }

    inner class ViewHolder(itemView: View, onNoteListener: OnNoteListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var onNoteListener = onNoteListener.let {
            it
        }

        override fun onClick(v: View) {
            onNoteListener?.onNoteClick(adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    interface OnNoteListener {
        fun onNoteClick(position: Int)
    }

}