package com.example.weather_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude


@Entity(tableName = "knowledge")
data class Knowledge(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val desc: String = "",
    @Exclude
    val thumbnail: ByteArray = ByteArray(0),
    val thumbnailBase64: String = ""
)


data class FirebaseKnowledge(
    val id: Int,
    val title: String = "",
    val desc: String = "",
    val thumbnailBase64: String = ""
)
