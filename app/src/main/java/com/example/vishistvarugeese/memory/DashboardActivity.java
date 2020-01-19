package com.example.vishistvarugeese.memory;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private FloatingActionMenu menuButton;

    private FloatingActionButton profile;
    private FloatingActionButton speed_dial;
    private FloatingActionButton face_recognition;
    //private FloatingActionButton notes;

    private Handler mUiHandler = new Handler();

    private TextView tv1, tv2, tv3, tv4, tv5;
    private Button btnProfile, btnContact, btnIdentifyPeople, btnNotes;
    private ImageView image;
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    private ActionBar actionbar;

    Animation animFadeIn0, animFadeIn1, animFadeIn2, animFadeIn3, animFadeIn4, animFadeIn5, animMove4, animMove2, animMove3, animMove5;
    int i;
    boolean visible = false;

    private List<View> views = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        overridePendingTransition(R.anim.right_to_left_slide_out, R.anim.right_to_left_slide_in);

        menuButton = (FloatingActionMenu) findViewById(R.id.menu);

        profile = (FloatingActionButton) findViewById(R.id.profile);
        speed_dial = (FloatingActionButton) findViewById(R.id.speed_dial);
        face_recognition = (FloatingActionButton) findViewById(R.id.face_recognition);
        //notes = (FloatingActionButton) findViewById(R.id.notes);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);

        btnProfile = findViewById(R.id.btnProfile);
        btnContact = findViewById(R.id.btnContact);
        btnIdentifyPeople = findViewById(R.id.btnIdentifyPeople);
        btnNotes = findViewById(R.id.btnNotes);

        image = (ImageView) findViewById(R.id.image);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Navigation Drawer
        configureToolbar();
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerlayout, toolbar, R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.navgation);
        mToggle.syncState();
        setUpDrawerContent(nvDrawer);

        animFadeIn0 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        animFadeIn1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        animFadeIn2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        animFadeIn3 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        animFadeIn4 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        animFadeIn5 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        animMove4 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);

        animMove2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);

        animMove3 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);

        animMove5 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);

        views.add(image);
        views.add(tv1);
        views.add(tv2);
        views.add(tv3);
        views.add(tv4);
        views.add(tv5);

        startAnimation();

    }

    void startAnimation(){
        int delay = 500;
        for (final View view : views) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(view.getId() == R.id.image) {
                        imageVisible();
                        menuButton.showMenuButton(true);
                    } else if(view.getId() == R.id.tv1){
                        textViewVisible(tv1, animFadeIn1);
                    } else if(view.getId() == R.id.tv2){
                        textViewVisible(tv2, animFadeIn2);
                        buttonVisible(btnProfile, animMove2);
                    } else if(view.getId() == R.id.tv3){
                        textViewVisible(tv3, animFadeIn3);
                        buttonVisible(btnContact, animMove3);
                    } else if(view.getId() == R.id.tv4){
                        textViewVisible(tv4, animFadeIn4);
                        buttonVisible(btnIdentifyPeople, animMove4);
                    } else if(view.getId() == R.id.tv5){
                        textViewVisible(tv5, animFadeIn5);
                        buttonVisible(btnNotes, animMove5);
                    }
                }
            }, delay);
            delay += 1000;
        }
    }

    void imageVisible(){
        image.setVisibility(View.VISIBLE);
        image.startAnimation(animFadeIn0);
    }

    void textViewVisible(TextView tv, Animation anim){
        tv.setVisibility(View.VISIBLE);
        tv.startAnimation(anim);
    }

    void buttonVisible(Button btn, Animation anim){
        btn.setVisibility(View.VISIBLE);
        btn.startAnimation(anim);
    }

    public void OnProfileClick(View view) {
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        finish();
    }


    public void OnIdentifyPeopleClick(View view) {
        startActivity(new Intent(getApplicationContext(), FaceRecognitionActivity.class));
        finish();
    }


    public void OnEmergencyContactClick(View view) {
        startActivity(new Intent(getApplicationContext(), EmergencyContactActivity.class));
        finish();
    }

    public void OnNotesClick(View view) {
        startActivity(new Intent(getApplicationContext(), NotesActivity.class));
        finish();
    }

    private void setUpDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return false;
            }
        });
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
    }

    public void selectItemDrawer(MenuItem menuitem){
        switch (menuitem.getItemId()){
            case R.id.profile_nav:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                break;
            case R.id.speed_dial_nav:
                startActivity(new Intent(getApplicationContext(), EmergencyContactActivity.class));
                break;
            case R.id.identify_people_nav:
                startActivity(new Intent(getApplicationContext(), FaceRecognitionActivity.class));
                break;
            case R.id.notes_nav:
                startActivity(new Intent(getApplicationContext(), NotesActivity.class));
                break;
            default:
                break;
        }
    }

}
