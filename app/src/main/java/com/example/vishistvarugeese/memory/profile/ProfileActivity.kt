package com.example.vishistvarugeese.memory.profile

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.vishistvarugeese.memory.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val prevIntent = intent
        val playAnim = prevIntent.getIntExtra("play_anim", 1)
        if (playAnim == 1) overridePendingTransition(R.anim.right_to_left_slide_in, R.anim.right_to_left_slide_out)
    }

    fun onEditDetailsClick(view: View?) {
        startActivity(Intent(applicationContext, DetailsActivity::class.java))
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
}