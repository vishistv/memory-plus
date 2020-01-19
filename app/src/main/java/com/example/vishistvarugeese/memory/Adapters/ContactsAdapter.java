package com.example.vishistvarugeese.memory.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishistvarugeese.memory.EmergencyContactActivity;
import com.example.vishistvarugeese.memory.Helper.SQLiteHandler;
import com.example.vishistvarugeese.memory.R;
import com.example.vishistvarugeese.memory.Variables.Contact_details;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{

    private CursorAdapter mCursorAdapter;
    private Context mContext;

    public ContactsAdapter(Context context, Cursor c, boolean autoRequery) {

        mContext = context;

        mCursorAdapter = new CursorAdapter(mContext, c, autoRequery) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
                view.setTag(new ViewHolder(view));
                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                Contact_details contact_details = new Contact_details(cursor);
                ViewHolder holder = (ViewHolder)view.getTag();

                holder.tv_contactName.setText(contact_details.getContactName());
                holder.tv_contactPhoneNumber.setText(contact_details.getContactPhoneNumber());
                holder.tv_contactEmail.setText(contact_details.getContactEmail());

                holder.iv_profilePic.setImageBitmap(contact_details.getProfilePic());


            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_contactName;
        private TextView tv_contactPhoneNumber;
        private TextView tv_contactEmail;
        private CircleImageView iv_profilePic;
        private LinearLayout ll_item;


        public ViewHolder(View itemView) {
            super(itemView);

            tv_contactName = (TextView) itemView.findViewById(R.id.tv_contact_name);
            tv_contactPhoneNumber = (TextView) itemView.findViewById(R.id.tv_contact_number);
            tv_contactEmail = (TextView) itemView.findViewById(R.id.tv_contact_email);
            iv_profilePic = (CircleImageView) itemView.findViewById(R.id.iv_contact_image);
            ll_item = (LinearLayout) itemView.findViewById(R.id.item);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        mCursorAdapter.getCursor().moveToPosition(position);
        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());

        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCursorAdapter.getCursor().moveToPosition(position);
                Contact_details contact_details = new Contact_details(mCursorAdapter.getCursor());
                String phoneNumber = contact_details.getContactPhoneNumber();
                Uri callUri = Uri.parse("tel://" + phoneNumber);
                Intent callIntent = new Intent(Intent.ACTION_DIAL,callUri);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                mContext.startActivity(callIntent);
                Toast.makeText(mContext, contact_details.getContactPhoneNumber(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.ll_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Cursor deleteCursor = mCursorAdapter.getCursor();
                deleteCursor.moveToPosition(position);
                final Contact_details contact_details = new Contact_details(deleteCursor);
                String phoneNumber = contact_details.getContactPhoneNumber();
                String name = contact_details.getContactName();

                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setCancelable(false);
                dialog.setTitle(name + ": " + phoneNumber);
                dialog.setMessage("Are you sure you want to delete this entry?" );
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        SQLiteHandler db = new SQLiteHandler(mContext);
                        db.deleteContact(contact_details.getContactName());
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount());
                        deleteCursor.requery();
                        if(db.getCount() == 0){
                            EmergencyContactActivity.noContacts();
                        }
                    }
                })
                        .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();
    }


}
