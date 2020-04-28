package com.example.vishistvarugeese.memory.Variables

import android.provider.BaseColumns

class Contact_Contract  //An empty private constructor makes sure that the class is not going to be initialised.
private constructor() {
    object ContactEntry : BaseColumns {
        const val TABLE_NAME = "contact_details"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE_NUMBER = "phoneNumber"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PHOTO = "photo"
    }
}