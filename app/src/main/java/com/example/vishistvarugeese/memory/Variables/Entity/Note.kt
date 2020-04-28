package com.example.vishistvarugeese.memory.Variables.Entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
class Note : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var title: String? = null
    var description: String? = null
    var timestamp: String? = null

}