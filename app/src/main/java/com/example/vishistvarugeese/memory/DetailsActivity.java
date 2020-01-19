package com.example.vishistvarugeese.memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        overridePendingTransition(R.anim.right_to_left_slide_in, R.anim.right_to_left_slide_out);

    }

    public void onBackPressed(View view) {
        Intent backIntent = new Intent(this, ProfileActivity.class);
        backIntent.putExtra("play_anim", 3);
        startActivity(backIntent);
        finish();
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(this, ProfileActivity.class);
        backIntent.putExtra("play_anim", 3);
        startActivity(backIntent);
        finish();
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out);
    }
}
