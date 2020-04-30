package com.example.vishistvarugeese.memory.dashboard

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import com.example.vishistvarugeese.memory.*
import com.example.vishistvarugeese.memory.contacts.EmergencyContactActivity
import com.example.vishistvarugeese.memory.faceRecognition.FaceRecognitionActivity
import com.example.vishistvarugeese.memory.notes.NotesActivity
import com.example.vishistvarugeese.memory.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.*

class DashboardActivity : AppCompatActivity() {
    private var animFadeIn0: Animation? = null
    private var animFadeIn1: Animation? = null
    private var animFadeIn2: Animation? = null
    private var animFadeIn3: Animation? = null
    private var animFadeIn4: Animation? = null
    private var animFadeIn5: Animation? = null
    private var animMove4: Animation? = null
    private var animMove2: Animation? = null
    private var animMove3: Animation? = null
    private var animMove5: Animation? = null

    private val mUiHandler = Handler()
    private var mToggle: ActionBarDrawerToggle? = null
    private var actionbar: ActionBar? = null

    private val views: MutableList<View?> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        //Navigation Drawer
        configureToolbar()
        mToggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)
        drawer?.addDrawerListener(mToggle!!)
        val nvDrawer = findViewById<View>(R.id.navgation) as NavigationView
        mToggle!!.syncState()
        setUpDrawerContent(nvDrawer)
        animFadeIn0 = AnimationUtils.loadAnimation(applicationContext,
                R.anim.fade_in)
        animFadeIn1 = AnimationUtils.loadAnimation(applicationContext,
                R.anim.fade_in)
        animFadeIn2 = AnimationUtils.loadAnimation(applicationContext,
                R.anim.fade_in)
        animFadeIn3 = AnimationUtils.loadAnimation(applicationContext,
                R.anim.fade_in)
        animFadeIn4 = AnimationUtils.loadAnimation(applicationContext,
                R.anim.fade_in)
        animFadeIn5 = AnimationUtils.loadAnimation(applicationContext,
                R.anim.fade_in)
        animMove4 = AnimationUtils.loadAnimation(applicationContext,
                R.anim.move)
        animMove2 = AnimationUtils.loadAnimation(applicationContext,
                R.anim.move)
        animMove3 = AnimationUtils.loadAnimation(applicationContext,
                R.anim.move)
        animMove5 = AnimationUtils.loadAnimation(applicationContext,
                R.anim.move)
        views.add(image)
        views.add(tv1)
        views.add(tv2)
        views.add(tv3)
        views.add(tv4)
        views.add(tv5)
        startAnimation()
    }

    fun startAnimation() {
        var delay = 500
        for (view in views) {
            mUiHandler.postDelayed({
                if (view!!.id == R.id.image) {
                    imageVisible()
                    menu?.showMenuButton(true)
                } else if (view.id == R.id.tv1) {
                    textViewVisible(tv1, animFadeIn1)
                } else if (view.id == R.id.tv2) {
                    textViewVisible(tv2, animFadeIn2)
                    buttonVisible(btnProfile, animMove2)
                } else if (view.id == R.id.tv3) {
                    textViewVisible(tv3, animFadeIn3)
                    buttonVisible(btnContact, animMove3)
                } else if (view.id == R.id.tv4) {
                    textViewVisible(tv4, animFadeIn4)
                    buttonVisible(btnIdentifyPeople, animMove4)
                } else if (view.id == R.id.tv5) {
                    textViewVisible(tv5, animFadeIn5)
                    buttonVisible(btnNotes, animMove5)
                }
            }, delay.toLong())
            delay += 100
        }
    }

    private fun imageVisible() {
        image?.visibility = View.VISIBLE
        image?.startAnimation(animFadeIn0)
    }

    private fun textViewVisible(tv: TextView?, anim: Animation?) {
        tv?.visibility = View.VISIBLE
        tv?.startAnimation(anim)
    }

    private fun buttonVisible(btn: Button?, anim: Animation?) {
        btn?.visibility = View.VISIBLE
        btn?.startAnimation(anim)
    }

    fun onProfileClick(view: View?) {
        closeFab()
        startActivity(Intent(applicationContext, ProfileActivity::class.java))
    }

    fun onIdentifyPeopleClick(view: View?) {
        closeFab()
        startActivity(Intent(applicationContext, FaceRecognitionActivity::class.java))
    }

    fun onEmergencyContactClick(view: View?) {
        closeFab()
        startActivity(Intent(applicationContext, EmergencyContactActivity::class.java))
    }

    fun onNotesClick(view: View?) {
        closeFab()
        startActivity(Intent(applicationContext, NotesActivity::class.java))
    }

    private fun setUpDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { item ->
            selectItemDrawer(item)
            false
        }
    }

    private fun closeFab() {
        if (menu.isOpened) {
            menu?.close(true)
        }
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        actionbar = supportActionBar
        actionbar?.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayShowTitleEnabled(false)
    }

    private fun selectItemDrawer(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.profile_nav -> startActivity(Intent(applicationContext, ProfileActivity::class.java))
            R.id.speed_dial_nav -> startActivity(Intent(applicationContext, EmergencyContactActivity::class.java))
            R.id.identify_people_nav -> startActivity(Intent(applicationContext, FaceRecognitionActivity::class.java))
            R.id.notes_nav -> startActivity(Intent(applicationContext, NotesActivity::class.java))
        }
    }
}