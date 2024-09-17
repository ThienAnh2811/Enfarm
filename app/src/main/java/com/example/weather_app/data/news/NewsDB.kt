package com.example.weather_app.data.news

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather_app.model.News

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class newsDB: RoomDatabase() {
    abstract fun newsDAO(): newsDAO
    companion object{
        @Volatile
        private var Instance: newsDB? = null
        fun getDatabase(context: Context): newsDB {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, newsDB::class.java, "news_db")
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }

}