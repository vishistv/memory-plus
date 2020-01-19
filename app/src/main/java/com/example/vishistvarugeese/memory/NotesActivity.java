package com.example.vishistvarugeese.memory;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.vishistvarugeese.memory.Adapters.NotesAdapter;
import com.example.vishistvarugeese.memory.Repositories.NoteRepository;
import com.example.vishistvarugeese.memory.Variables.Entity.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesActivity extends AppCompatActivity implements NotesAdapter.OnNoteListener {
    private static final String TAG = "NotesActivity";
    ArrayList<Note> notesList = new ArrayList<>();
    NotesAdapter notesAdapter;
    RecyclerView rvNotes;
    NotesAdapter.OnNoteListener nl;
    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        overridePendingTransition(R.anim.right_to_left_slide_in, R.anim.right_to_left_slide_out);

        parentLayout = findViewById(R.id.parent_view);

        rvNotes = findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));

        nl = this;

        NoteRepository noteRepository = new NoteRepository();
//        noteRepository.deleteAll(getApplicationContext());

        noteRepository.getAllNotes(getApplicationContext()).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                if (notes.size() > 0) {
                    notesList = new ArrayList<Note>(notes);
                    notesAdapter = new NotesAdapter(nl, notesList);
                    rvNotes.setAdapter(notesAdapter);
                }
            }
        });


    }

    public void onWriteNotesClick(View view) {
        final Dialog dialog = new Dialog(NotesActivity.this);
        dialog.setContentView(R.layout.note_dialog);
        final EditText etDialogTitle = dialog.findViewById(R.id.et_dialog_title);
        final EditText etDialogNote = dialog.findViewById(R.id.et_dialog_note);
        Button btnCancel = dialog.findViewById(R.id.btn_dialog_cancel);
        Button btnSave = dialog.findViewById(R.id.btn_dialog_edit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etDialogTitle.getText().toString();
                String description = etDialogNote.getText().toString();

                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy, MMM d, h:mm a");
                String timestamp = sdf.format(d);

                Note note = new Note();
                note.setTitle(title);
                note.setDescription(description);
                note.setTimestamp(timestamp);

                NoteRepository noteRepository = new NoteRepository();
                noteRepository.insertNote(note, getApplicationContext());

                notesList.add(note);
                if(notesAdapter == null){
                    notesAdapter = new NotesAdapter(nl, notesList);
                    rvNotes.setAdapter(notesAdapter);
                } else {
                    notesAdapter.notifyDataSetChanged();
                }
                dialog.dismiss();

            }
        });
    }

    public void onEditNotesClick(String title, String note, final int id, final int position) {
        final Dialog dialog = new Dialog(NotesActivity.this);
        dialog.setContentView(R.layout.note_dialog);
        final EditText etDialogTitle = dialog.findViewById(R.id.et_dialog_title);
        final EditText etDialogNote = dialog.findViewById(R.id.et_dialog_note);

        etDialogTitle.setText(title);
        etDialogNote.setText(note);

        Button btnCancel = dialog.findViewById(R.id.btn_dialog_cancel);
        Button btnSave = dialog.findViewById(R.id.btn_dialog_edit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etDialogTitle.getText().toString();
                String description = etDialogNote.getText().toString();

                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy, MMM d, h:mm a");
                String timestamp = sdf.format(d);

                NoteRepository noteRepository = new NoteRepository();
                Note note = new Note();
                note.setTitle(title);
                note.setDescription(description);
                note.setTimestamp(timestamp);
                noteRepository.updateNote(title, description, timestamp, id, getApplicationContext());

                notesList.set(position, note);
                notesAdapter.notifyDataSetChanged();
                dialog.dismiss();

                Snackbar.make(parentLayout, "Note Updated Successfully!", Snackbar.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onNoteClick(final int position) {
        Log.d("Hello!", "onNoteClick: ");
//        Note note = notes.get(position);
        Note note = notesList.get(position);
        final String title = note.getTitle();
        final String description = note.getDescription();
        final String timestamp = note.getTimestamp();
        final int id = note.getId();

        final Dialog dialog = new Dialog(NotesActivity.this);
        dialog.setContentView(R.layout.note_view_dialog);
        final TextView tvNoteDialogTitle = dialog.findViewById(R.id.tv_note_dialog_title);
        final TextView tvNoteDialogDescription = dialog.findViewById(R.id.tv_note_dialog_description);
        tvNoteDialogDescription.setMovementMethod(new ScrollingMovementMethod());
        final TextView tvNoteDialogTimestamp = dialog.findViewById(R.id.tv_note_dialog_timestamp);
        Button btnCancel = dialog.findViewById(R.id.btn_note_dialog_cancel);
        Button btnEdit = dialog.findViewById(R.id.btn_note_dialog_edit);
        ImageButton btnDelete = dialog.findViewById(R.id.iv_note_dialog_delete);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvNoteDialogTitle.setText(title);
        tvNoteDialogDescription.setText(description);
        tvNoteDialogTimestamp.setText(timestamp);
        dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditNotesClick(title, description, id, position);
                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteRepository noteRepository = new NoteRepository();
                noteRepository.deleteNote(id, getApplicationContext());
                notesList.remove(position);
                notesAdapter.notifyDataSetChanged();
                dialog.dismiss();

                Snackbar.make(parentLayout, "Note Deleted Successfully!", Snackbar.LENGTH_LONG).show();
            }
        });


    }

    public void onBackPressed(View view) {
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out);
    }
}
