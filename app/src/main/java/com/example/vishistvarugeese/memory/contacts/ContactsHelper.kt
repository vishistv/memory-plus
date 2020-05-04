package com.example.vishistvarugeese.memory.contacts

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Base64
import java.io.ByteArrayOutputStream

object ContactsHelper {

    private val PREFERRED_WIDTH = 250f
    private val PREFERRED_HEIGHT = 250f

    @JvmStatic
    fun stringToBitmap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }

    @JvmStatic
    fun bitmapToString(bitmap: Bitmap): String {
        var resizedBitmap = resizeBitmap(bitmap)
        val baos = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun resizeBitmap(bitmap: Bitmap): Bitmap {
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