package com.example.vishistvarugeese.memory.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vishistvarugeese.memory.R;
import com.example.vishistvarugeese.memory.Variables.Entity.Note;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private ArrayList<Note> notesList = new ArrayList<>();
    private OnNoteListener onNoteListener;

    public NotesAdapter(OnNoteListener onNoteListener, ArrayList<Note> notesList){
        this.notesList = notesList;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_item, parent, false);
        return new ViewHolder(v, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note noteItem = notesList.get(position);
        holder.tv_noteDescription.setText(noteItem.getDescription());
        holder.tv_noteTitle.setText((noteItem.getTitle()));
        holder.tv_noteTimestamp.setText(noteItem.getTimestamp());

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv_noteTitle;
        private TextView tv_noteDescription;
        private TextView tv_noteTimestamp;

        OnNoteListener mOnNoteListener;


        public ViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            tv_noteTitle = (TextView) itemView.findViewById(R.id.tv_note_title);
            tv_noteDescription = (TextView) itemView.findViewById(R.id.tv_note_dialog_description);
            tv_noteTimestamp = (TextView) itemView.findViewById(R.id.tv_note_timestamp);
            mOnNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
