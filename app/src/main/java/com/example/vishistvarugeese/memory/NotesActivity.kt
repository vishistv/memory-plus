package com.example.vishistvarugeese.memory

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.vishistvarugeese.memory.Adapters.NotesAdapter
import com.example.vishistvarugeese.memory.Adapters.NotesAdapter.OnNoteListener
import com.example.vishistvarugeese.memory.NotesActivity
import com.example.vishistvarugeese.memory.Repositories.NoteRepository
import com.example.vishistvarugeese.memory.Variables.Entity.Note
import java.text.SimpleDateFormat
import java.util.*

class NotesActivity : AppCompatActivity(), OnNoteListener {
    var notesList = ArrayList<Note>()
    var notesAdapter: NotesAdapter? = null
    var rvNotes: RecyclerView? = null
    var nl: OnNoteListener? = null
    var parentLayout: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        overridePendingTransition(R.anim.right_to_left_slide_in, R.anim.right_to_left_slide_out)
        parentLayout = findViewById(R.id.parent_view)
        rvNotes = findViewById(R.id.rv_notes)
        rvNotes.setLayoutManager(LinearLayoutManager(this))
        nl = this
        val noteRepository = NoteRepository()
        //        noteRepository.deleteAll(getApplicationContext());
        noteRepository.getAllNotes(applicationContext).observe(this, Observer { notes ->
            if (notes!!.size > 0) {
                notesList = ArrayList(notes)
                notesAdapter = NotesAdapter(nl, notesList)
                rvNotes.setAdapter(notesAdapter)
            }
        })
    }

    fun onWriteNotesClick(view: View?) {
        val dialog = Dialog(this@NotesActivity)
        dialog.setContentView(R.layout.note_dialog)
        val etDialogTitle = dialog.findViewById<EditText>(R.id.et_dialog_title)
        val etDialogNote = dialog.findViewById<EditText>(R.id.et_dialog_note)
        val btnCancel = dialog.findViewById<Button>(R.id.btn_dialog_cancel)
        val btnSave = dialog.findViewById<Button>(R.id.btn_dialog_edit)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        btnCancel.setOnClickListener { dialog.dismiss() }
        btnSave.setOnClickListener {
            val title = etDialogTitle.text.toString()
            val description = etDialogNote.text.toString()
            val d = Date()
            val sdf = SimpleDateFormat("d MMM yyyy h:mm a")
            val timestamp = sdf.format(d)
            val note = Note()
            note.title = title
            note.description = description
            note.timestamp = timestamp
            val noteRepository = NoteRepository()
            noteRepository.insertNote(note, applicationContext)
            notesList.add(note)
            if (notesAdapter == null) {
                notesAdapter = NotesAdapter(nl, notesList)
                rvNotes!!.adapter = notesAdapter
            } else {
                notesAdapter!!.notifyDataSetChanged()
            }
            dialog.dismiss()
        }
    }

    fun onEditNotesClick(title: String?, note: String?, id: Int, position: Int) {
        val dialog = Dialog(this@NotesActivity)
        dialog.setContentView(R.layout.note_dialog)
        val etDialogTitle = dialog.findViewById<EditText>(R.id.et_dialog_title)
        val etDialogNote = dialog.findViewById<EditText>(R.id.et_dialog_note)
        etDialogTitle.setText(title)
        etDialogNote.setText(note)
        val btnCancel = dialog.findViewById<Button>(R.id.btn_dialog_cancel)
        val btnSave = dialog.findViewById<Button>(R.id.btn_dialog_edit)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        btnCancel.setOnClickListener { dialog.dismiss() }
        btnSave.setOnClickListener {
            val title = etDialogTitle.text.toString()
            val description = etDialogNote.text.toString()
            val d = Date()
            val sdf = SimpleDateFormat("yyyy, MMM d, h:mm a")
            val timestamp = sdf.format(d)
            val noteRepository = NoteRepository()
            val note = Note()
            note.title = title
            note.description = description
            note.timestamp = timestamp
            noteRepository.updateNote(title, description, timestamp, id, applicationContext)
            notesList[position] = note
            notesAdapter!!.notifyDataSetChanged()
            dialog.dismiss()
            Snackbar.make(parentLayout!!, "Note Updated Successfully!", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onNoteClick(position: Int) {
        Log.d("Hello!", "onNoteClick: ")
        //        Note note = notes.get(position);
        val note = notesList[position]
        val title = note.title
        val description = note.description
        val timestamp = note.timestamp
        val id = note.id
        val dialog = Dialog(this@NotesActivity)
        dialog.setContentView(R.layout.note_view_dialog)
        val tvNoteDialogTitle = dialog.findViewById<TextView>(R.id.tv_note_dialog_title)
        val tvNoteDialogDescription = dialog.findViewById<TextView>(R.id.tv_note_dialog_description)
        tvNoteDialogDescription.movementMethod = ScrollingMovementMethod()
        val tvNoteDialogTimestamp = dialog.findViewById<TextView>(R.id.tv_note_dialog_timestamp)
        val btnCancel = dialog.findViewById<Button>(R.id.btn_note_dialog_cancel)
        val btnEdit = dialog.findViewById<Button>(R.id.btn_note_dialog_edit)
        val btnDelete = dialog.findViewById<ImageButton>(R.id.iv_note_dialog_delete)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        tvNoteDialogTitle.text = title
        tvNoteDialogDescription.text = description
        tvNoteDialogTimestamp.text = timestamp
        dialog.show()
        btnCancel.setOnClickListener { dialog.dismiss() }
        btnEdit.setOnClickListener {
            onEditNotesClick(title, description, id, position)
            dialog.dismiss()
        }
        btnDelete.setOnClickListener {
            val noteRepository = NoteRepository()
            noteRepository.deleteNote(id, applicationContext)
            notesList.removeAt(position)
            notesAdapter!!.notifyDataSetChanged()
            dialog.dismiss()
            Snackbar.make(parentLayout!!, "Note Deleted Successfully!", Snackbar.LENGTH_LONG).show()
        }
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
        private const val TAG = "NotesActivity"
    }
}