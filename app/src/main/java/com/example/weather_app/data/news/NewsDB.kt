package com.example.weather_app.data.news

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather_app.data.knowledge.knowledgeDAO
import com.example.weather_app.model.Knowledge
import com.example.weather_app.model.News

// Your Room Database definition including two entities: News and Knowledge
@Database(entities = [News::class, Knowledge::class], version = 2, exportSchema = false)
abstract class newsDB: RoomDatabase() {
    abstract fun newsDAO(): newsDAO
    abstract fun knowledgeDAO(): knowledgeDAO

    companion object {
        @Volatile
        private var Instance: newsDB? = null

        fun getDatabase(context: Context): newsDB {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, newsDB::class.java, "o_db")
                    .fallbackToDestructiveMigration()  // Allows database migration when schema changes
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }
}
