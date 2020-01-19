package com.example.vishistvarugeese.memory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vishistvarugeese.memory.Helper.SQLiteHandler;
import com.example.vishistvarugeese.memory.Variables.Contact_details;

import java.io.IOException;
import java.io.InputStream;

public class EditAdd_ContactActivity extends AppCompatActivity {

    private ImageView iv_add_contact_image;
    private TextView tv_toolbar_contact_title;
    private EditText et_add_contact_name;
    private EditText et_add_contact_phoneNumber;
    private EditText et_add_contact_email;
    private Button btn_edit_add_contact;
    private Button btn_delete_contact;

    private String contact_name;
    private String contact_phoneNumber;
    private String contact_email;
    private String contact_pic;
    String picturePath;

    private static final float PREFERRED_WIDTH = 60;
    private static final float PREFERRED_HEIGHT = 60;

    private boolean imageSet = false;

    private int[] default_contact_pic = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four};

    private String KEY_CONTACT_PIC = "defaultContactPics";
    private String PREF_NAME = "memoryPlus";
    private int PRIVATE_MODE = 0;

    private int GALLERY_REQUEST_CODE = 1;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private SQLiteHandler db;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add__contact);

        overridePendingTransition(R.anim.right_to_left_slide_in, R.anim.right_to_left_slide_out);

        Intent intent = getIntent();
        int i = intent.getIntExtra("ADD", 0);

        pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

        tv_toolbar_contact_title = (TextView) findViewById(R.id.toolbar_contact_title);
        iv_add_contact_image = (ImageView) findViewById(R.id.iv_contact_set_image);
        et_add_contact_name = (EditText) findViewById(R.id.et_contact_set_name);
        et_add_contact_phoneNumber = (EditText) findViewById(R.id.et_contact_set_phoneNumber);
        et_add_contact_email = (EditText) findViewById(R.id.et_contact_set_email);
        btn_edit_add_contact = (Button) findViewById(R.id.btn_edit_add_contact);
        btn_delete_contact = (Button) findViewById(R.id.btn_delete_contact);

        db = new SQLiteHandler(getApplicationContext());

        //ONLY FOR TESTING
//        et_add_contact_name.setText("Rahul Varma");
//        et_add_contact_phoneNumber.setText("09442799642");
//        et_add_contact_email.setText("test@gmail.com");

        if(i == 1) {
            tv_toolbar_contact_title.setText("A D D  C O N T A C T");
            btn_edit_add_contact.setText("S A V E  C O N T A C T");
        }
        else {
            tv_toolbar_contact_title.setText("E D I T  C O N T A C T");
            btn_edit_add_contact.setText("S A V E  C H A N G E S");
            btn_delete_contact.setVisibility(View.VISIBLE);
        }
    }

    public void onEditAddContact(View view) {

        contact_name = et_add_contact_name.getText().toString();
        contact_phoneNumber = et_add_contact_phoneNumber.getText().toString();
        contact_email = et_add_contact_email.getText().toString();

        Bitmap image = ((BitmapDrawable)iv_add_contact_image.getDrawable()).getBitmap();

        new SQLiteHandler(this).addContacts(new Contact_details(contact_name, contact_phoneNumber, contact_email, image));

        Intent contactsIntent = new Intent(getApplicationContext(), EmergencyContactActivity.class);
        startActivity(contactsIntent);
        finish();
    }


    public void onAddImageClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                iv_add_contact_image.setImageBitmap(BitmapFactory.decodeStream(imageStream));
                iv_add_contact_image.setTag("image_set");

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

    }


    public void onDeleteContacts(View view) {
    }

    public void onBackPressed(View view) {
        Intent backIntent = new Intent(this, EmergencyContactActivity.class);
        backIntent.putExtra("play_anim", 3);
        startActivity(backIntent);
        finish();
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(this, EmergencyContactActivity.class);
        backIntent.putExtra("play_anim", 3);
        startActivity(backIntent);
        finish();
        overridePendingTransition(R.anim.left_to_right_slide_in, R.anim.left_to_right_slide_out);
    }


//    private boolean checkIfAlreadyhavePermission() {
//        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        return result == PackageManager.PERMISSION_GRANTED;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 1: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    startActivityForResult(i, 1);
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Please give your permission.", Toast.LENGTH_LONG).show();
//                }
//                break;
//            }
//        }
//    }

}
