package com.example.vishistvarugeese.memory.contacts

import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Base64
import java.io.ByteArrayOutputStream

class ContactDetails {
    private var contactName: String? = null
    private var contactPhoneNumber: String? = null
    private var contactEmail: String? = null
    private var imageAsString: String? = null

    constructor(cursor: Cursor) {
        contactName = cursor.getString(COL_NAME)
        contactPhoneNumber = cursor.getString(COL_PHONE)
        contactEmail = cursor.getString(COL_EMAIL)
        imageAsString = cursor.getString(COL_PIC)
    }

    constructor(contactName: String?, contactPhoneNumber: String?, contactEmail: String?, profilePic: Bitmap) {
        this.contactName = contactName
        this.contactPhoneNumber = contactPhoneNumber
        this.contactEmail = contactEmail
        imageAsString = bitmapToString(resizeBitmap(profilePic))
    }

    fun getProfilePic(): Bitmap? {
        return stringToBitmap(imageAsString)
    }

    fun getImageAsString(): String? {
        return imageAsString
    }

    fun getContactName(): String? {
        return contactName
    }

    fun getContactPhoneNumber(): String? {
        return contactPhoneNumber
    }

    fun getContactEmail(): String? {
        return contactEmail
    }

    companion object {
        private const val PREFERRED_WIDTH = 250f
        private const val PREFERRED_HEIGHT = 250f
        const val COL_NAME = 1
        const val COL_PHONE = 2
        const val COL_EMAIL = 3
        const val COL_PIC = 4
        private fun stringToBitmap(encodedString: String?): Bitmap? {
            return try {
                val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
            } catch (e: Exception) {
                e.message
                null
            }
        }

        private fun bitmapToString(bitmap: Bitmap): String {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val b = baos.toByteArray()
            return Base64.encodeToString(b, Base64.DEFAULT)
        }

        fun resizeBitmap(bitmap: Bitmap): Bitmap {
            val width = bitmap.width
            val height = bitmap.height
            val scaleWidth = PREFERRED_WIDTH / width
            val scaleHeight = PREFERRED_HEIGHT / height
            val matrix = Matrix()
            matrix.postScale(scaleWidth, scaleHeight)
            val resizedBitmap = Bitmap.createBitmap(
                    bitmap, 0, 0, width, height, matrix, false)
            bitmap.recycle()
            return resizedBitmap
        }
    }
}