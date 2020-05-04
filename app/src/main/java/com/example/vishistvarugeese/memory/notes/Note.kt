package com.example.vishistvarugeese.memory.notes

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Note : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var title: String? = null
    var description: String? = null
    var timestamp: String? = null

}