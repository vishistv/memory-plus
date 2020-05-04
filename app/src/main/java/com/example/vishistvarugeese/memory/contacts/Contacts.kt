package com.example.vishistvarugeese.memory.contacts

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.provider.BaseColumns
import java.io.Serializable

@Entity
data class Contacts(
                    val name: String,
                    val phoneNumber: String,
                    val email: String,
                    val imageAsString: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
