package com.example.weather_app.model

import android.util.Base64
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.google.firebase.database.Exclude


@Entity(tableName = "news")
data class News(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val desc: String = "",
    @Exclude  // Important for Firebase
    val thumbnail: ByteArray = ByteArray(0),
    val thumbnailBase64: String = ""
)

data class FirebaseNews(
    val id: Int = 0,
    val title: String = "",
    val desc: String = "",
    val thumbnailBase64: String = ""
)
