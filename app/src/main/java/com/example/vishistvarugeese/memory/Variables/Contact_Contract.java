package com.example.vishistvarugeese.memory.Variables;

import android.provider.BaseColumns;

public class Contact_Contract {

    //An empty private constructor makes sure that the class is not going to be initialised.
    private Contact_Contract() {}

    public static final class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "contact_details";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE_NUMBER = "phoneNumber";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHOTO = "photo";
    }
}
