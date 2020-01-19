package com.example.vishistvarugeese.memory.Variables;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Contact_details {

    private String contactName;
    private String contactPhoneNumber;
    private String contactEmail;
    private String profilePic;

    private static final float PREFERRED_WIDTH = 250;
    private static final float PREFERRED_HEIGHT = 250;

    public static final int COL_NAME = 1;
    public static final int COL_PHONE = 2;
    public static final int COL_EMAIL = 3;
    public static final int COL_PIC = 4;

    public Contact_details(Cursor cursor) {
        this.contactName = cursor.getString(COL_NAME);
        this.contactPhoneNumber = cursor.getString(COL_PHONE);
        this.contactEmail = cursor.getString(COL_EMAIL);
        this.profilePic = cursor.getString(COL_PIC);
    }

    public Contact_details(String contactName, String contactPhoneNumber, String contactEmail, Bitmap profilePic){
        this.contactName = contactName;
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactEmail = contactEmail;
        this.profilePic = bitmapToString(resizeBitmap(profilePic));
    }


    public String getContactName() {
        return this.contactName;
    }

    public String getContactPhoneNumber() {
        return this.contactPhoneNumber;
    }

    public String getContactEmail(){ return this.contactEmail; }

    public Bitmap getProfilePic() {
        return stringToBitmap(this.profilePic);
    }

    public String getImageAsString() {
        return this.profilePic;
    }

    private static Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap resizeBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = PREFERRED_WIDTH / width;
        float scaleHeight = PREFERRED_HEIGHT / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return resizedBitmap;
    }

}
