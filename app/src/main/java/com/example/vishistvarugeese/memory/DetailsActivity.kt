package com.example.vishistvarugeese.memory

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        overridePendingTransition(R.anim.right_to_left_slide_in, R.anim.right_to_left_slide_out)
    }

    fun onBackPressed(view: View?) {
//        Intent backIntent = new Intent(this, ProfileActivity.class);
//        backIntent.putExtra("play_anim", 3);
//        startActivity(backIntent);
        finish()
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //        Intent backIntent = new Intent(this, ProfileActivity.class);
//        backIntent.putExtra("play_anim", 3);
//        startActivity(backIntent);
        finish()
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out)
    }
}