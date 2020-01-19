package com.example.vishistvarugeese.memory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.vishistvarugeese.memory.Adapters.ContactsAdapter;
import com.example.vishistvarugeese.memory.Helper.SQLiteHandler;

public class EmergencyContactActivity extends AppCompatActivity {
    private static final String TAG = "EmergencyContact";

    private SQLiteHandler dbHelper;
    private static RecyclerView recyclerView;

    private static LinearLayout ll_noContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);

        Intent prevIntent = getIntent();
        int playAnim = prevIntent.getIntExtra("play_anim", 1);
        Log.d(TAG, playAnim + "");
        if(playAnim == 1)
            overridePendingTransition(R.anim.right_to_left_slide_in, R.anim.right_to_left_slide_out);

        this.dbHelper = new SQLiteHandler(this);
        recyclerView = (RecyclerView) findViewById(R.id.contact_list);
        ll_noContacts = (LinearLayout) findViewById(R.id.no_contacts);

//        dbHelper.deleteContacts();

        if(dbHelper.getCount() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            ll_noContacts.setVisibility(View.GONE);
            this.dbHelper = new SQLiteHandler(this);
            this.recyclerView.setAdapter(new ContactsAdapter(this, this.dbHelper.readAllMemories(), false));
            this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            ll_noContacts.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    public void onAddContacts(View view) {
        Intent addContactsIntent = new Intent(getApplicationContext(), EditAdd_ContactActivity.class);
        addContactsIntent.putExtra("ADD", 1);
        startActivity(addContactsIntent);
        finish();
    }

    public static void noContacts(){
        ll_noContacts.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
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
