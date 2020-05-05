package com.example.vishistvarugeese.memory.notes

import android.app.Dialog
import androidx.lifecycle.Observer
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.vishistvarugeese.memory.R
import com.example.vishistvarugeese.memory.contacts.ContactsViewModel
import com.example.vishistvarugeese.memory.notes.NotesAdapter.OnNoteListener
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.note_dialog.*
import kotlinx.android.synthetic.main.note_view_dialog.*
import java.text.SimpleDateFormat
import java.util.*

class NotesActivity : AppCompatActivity(), OnNoteListener {
    private var mNotesList = ArrayList<Note>()
    private var mNotesAdapter: NotesAdapter? = null
    private var mOnNoteListener: OnNoteListener? = null
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        overridePendingTransition(R.anim.right_to_left_slide_in, R.anim.right_to_left_slide_out)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        rv_notes.layoutManager = LinearLayoutManager(this)
        mOnNoteListener = this

        mNoteViewModel.allNote.observe(this, Observer { notes ->
            if (notes.isNotEmpty()) {
                mNotesList = ArrayList(notes)
                mNotesAdapter = NotesAdapter(mOnNoteListener, mNotesList)
                rv_notes?.adapter = mNotesAdapter
            }
        })
    }

    fun onWriteNotesClick(view: View?) {
        val dialog = Dialog(this@NotesActivity)
        dialog.setContentView(R.layout.note_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.btn_dialog_cancel.setOnClickListener { dialog.dismiss() }
        dialog.btn_dialog_save.setOnClickListener {
            val title = dialog.et_dialog_title.text.toString()
            val description = dialog.et_dialog_note.text.toString()
            val timestamp = SimpleDateFormat("d MMM yyyy h:mm a").format(Date())

            val note = Note()
            note.title = title
            note.description = description
            note.timestamp = timestamp
            mNoteViewModel.insert(note)
            mNotesList.add(note)
            if (mNotesAdapter == null) {
                mNotesAdapter = NotesAdapter(mOnNoteListener, mNotesList)
                rv_notes?.adapter = mNotesAdapter
            } else {
                mNotesAdapter?.notifyDataSetChanged()
            }
            dialog.dismiss()
        }
    }

    private fun onEditNotesClick(mTitle: String?, mNote: String?, id: Int, position: Int) {
        val dialog = Dialog(this@NotesActivity)
        dialog.setContentView(R.layout.note_dialog)
        dialog.et_dialog_title.setText(mTitle)
        dialog.et_dialog_note.setText(mNote)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.btn_dialog_cancel.setOnClickListener { dialog.dismiss() }
        dialog.btn_dialog_save.setOnClickListener {
            val title = dialog.et_dialog_title.text.toString()
            val description = dialog.et_dialog_note.text.toString()
            val timestamp = SimpleDateFormat("d MMM yyyy h:mm a").format(Date())
            val note = Note()
            note.title = title
            note.description = description
            note.timestamp = timestamp
            mNoteViewModel.update(title, description, timestamp, id)
            mNotesList[position] = note
            mNotesAdapter?.notifyDataSetChanged()
            dialog.dismiss()
            Snackbar.make(parent_view, "Note Updated Successfully!", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onNoteClick(position: Int) {
        Log.d("Hello!", "onNoteClick: ")
        //        Note note = notes.get(position);
        val note = mNotesList[position]
        val title = note.title
        val description = note.description
        val timestamp = note.timestamp
        val id = note.id
        val dialog = Dialog(this@NotesActivity)
        dialog.setContentView(R.layout.note_view_dialog)
        dialog.tv_note_dialog_description.movementMethod = ScrollingMovementMethod()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.tv_note_dialog_title.text = title
        dialog.tv_note_dialog_description.text = description
        dialog.tv_note_dialog_timestamp.text = timestamp
        dialog.show()
        dialog.btn_note_dialog_cancel.setOnClickListener { dialog.dismiss() }
        dialog.btn_note_dialog_edit.setOnClickListener {
            onEditNotesClick(title, description, id, position)
            dialog.dismiss()
        }
        dialog.iv_note_dialog_delete.setOnClickListener {
            mNoteViewModel.delete(id)
            mNotesList.removeAt(position)
            mNotesAdapter?.notifyDataSetChanged()
            dialog.dismiss()
            Snackbar.make(parent_view, "Note Deleted Successfully!", Snackbar.LENGTH_LONG).show()
        }
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

    companion object {
        private const val TAG = "NotesActivity"
    }
}