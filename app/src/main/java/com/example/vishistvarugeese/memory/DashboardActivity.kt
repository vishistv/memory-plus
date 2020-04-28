package com.example.vishistvarugeese.memory

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import java.util.*

class DashboardActivity : AppCompatActivity() {
    var animFadeIn0: Animation? = null
    var animFadeIn1: Animation? = null
    var animFadeIn2: Animation? = null
    var animFadeIn3: Animation? = null
    var animFadeIn4: Animation? = null
    var animFadeIn5: Animation? = null
    var animMove4: Animation? = null
    var animMove2: Animation? = null
    var animMove3: Animation? = null
    var animMove5: Animation? = null
    var i = 0
    var visible = false
    private var menuButton: FloatingActionMenu? = null

    //private FloatingActionButton notes;
    private var profile: FloatingActionButton? = null
    private var speed_dial: FloatingActionButton? = null
    private var face_recognition: FloatingActionButton? = null
    private val mUiHandler = Handler()
    private var tv1: TextView? = null
    private var tv2: TextView? = null
    private var tv3: TextView? = null
    private var tv4: TextView? = null
    private var tv5: TextView? = null
    private var btnProfile: Button? = null
    private var btnContact: Button? = null
    private var btnIdentifyPeople: Button? = null
    private var btnNotes: Button? = null
    private var image: ImageView? = null
    private var mDrawerlayout: DrawerLayout? = null
    private var mToggle: ActionBarDrawerToggle? = null
    private var toolbar: Toolbar? = null
    private var actionbar: ActionBar? = null
    private val views: MutableList<View?> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

//        overridePendingTransition(R.anim.right_to_left_slide_out, R.anim.right_to_left_slide_in);
        menuButton = findViewById<View>(R.id.menu) as FloatingActionMenu
        profile = findViewById<View>(R.id.profile) as FloatingActionButton
        speed_dial = findViewById<View>(R.id.speed_dial) as FloatingActionButton
        face_recognition = findViewById<View>(R.id.face_recognition) as FloatingActionButton
        //notes = (FloatingActionButton) findViewById(R.id.notes);
        tv1 = findViewById<View>(R.id.tv1) as TextView
        tv2 = findViewById<View>(R.id.tv2) as TextView
        tv3 = findViewById<View>(R.id.tv3) as TextView
        tv4 = findViewById<View>(R.id.tv4) as TextView
        tv5 = findViewById<View>(R.id.tv5) as TextView
        btnProfile = findViewById(R.id.btnProfile)
        btnContact = findViewById(R.id.btnContact)
        btnIdentifyPeople = findViewById(R.id.btnIdentifyPeople)
        btnNotes = findViewById(R.id.btnNotes)
        image = findViewById<View>(R.id.image) as ImageView
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        //Navigation Drawer
        configureToolbar()
        mDrawerlayout = findViewById<View>(R.id.drawer) as DrawerLayout
        mToggle = ActionBarDrawerToggle(this, mDrawerlayout, toolbar, R.string.open, R.string.close)
        mDrawerlayout!!.addDrawerListener(mToggle!!)
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
                    menuButton!!.showMenuButton(true)
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

    fun imageVisible() {
        image!!.visibility = View.VISIBLE
        image!!.startAnimation(animFadeIn0)
    }

    fun textViewVisible(tv: TextView?, anim: Animation?) {
        tv!!.visibility = View.VISIBLE
        tv.startAnimation(anim)
    }

    fun buttonVisible(btn: Button?, anim: Animation?) {
        btn!!.visibility = View.VISIBLE
        btn.startAnimation(anim)
    }

    fun OnProfileClick(view: View?) {
        closeFab()
        startActivity(Intent(applicationContext, ProfileActivity::class.java))
    }

    fun OnIdentifyPeopleClick(view: View?) {
        closeFab()
        startActivity(Intent(applicationContext, FaceRecognitionActivity::class.java))
    }

    fun OnEmergencyContactClick(view: View?) {
        closeFab()
        startActivity(Intent(applicationContext, EmergencyContactActivity::class.java))
    }

    fun OnNotesClick(view: View?) {
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
        if (menuButton!!.isOpened) {
            menuButton!!.close(true)
        }
    }

    private fun configureToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        actionbar = supportActionBar
        actionbar!!.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayShowTitleEnabled(false)
    }

    fun selectItemDrawer(menuitem: MenuItem) {
        when (menuitem.itemId) {
            R.id.profile_nav -> startActivity(Intent(applicationContext, ProfileActivity::class.java))
            R.id.speed_dial_nav -> startActivity(Intent(applicationContext, EmergencyContactActivity::class.java))
            R.id.identify_people_nav -> startActivity(Intent(applicationContext, FaceRecognitionActivity::class.java))
            R.id.notes_nav -> startActivity(Intent(applicationContext, NotesActivity::class.java))
            else -> {
            }
        }
    }
}