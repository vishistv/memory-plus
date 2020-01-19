package com.example.vishistvarugeese.memory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent prevIntent = getIntent();
        int playAnim = prevIntent.getIntExtra("play_anim", 1);
        if(playAnim == 1)
            overridePendingTransition(R.anim.right_to_left_slide_in, R.anim.right_to_left_slide_out);
    }

    public void onEditDetailsClick(View view) {
        startActivity(new Intent(getApplicationContext(), DetailsActivity.class));
    }

    public void onBackPressed(View view) {
        Intent backIntent = new Intent(this, DashboardActivity.class);
        startActivity(backIntent);
        finish();
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(this, DashboardActivity.class);
        startActivity(backIntent);
        finish();
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out);
    }


}
