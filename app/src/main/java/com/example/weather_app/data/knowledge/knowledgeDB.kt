//package com.example.weather_app.data.knowledge
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.example.weather_app.data.news.newsDAO
//import com.example.weather_app.model.Knowledge
//import com.example.weather_app.model.News
//
//
//@Database(entities = [News::class, Knowledge::class], version = 2, exportSchema = false)
//abstract class newsDB: RoomDatabase() {
//    abstract fun newsDAO(): newsDAO
//    abstract fun knowledgeDB(): knowledgeDAO
//    companion object {
//        @Volatile
//        private var Instance: newsDB? = null
//
//        fun getDatabase(context: Context): newsDB {
//            return Instance ?: synchronized(this) {
//                Room.databaseBuilder(context, newsDB::class.java, "o_db")
//                    .fallbackToDestructiveMigration()
//                    .build()
//                    .also {
//                        Instance = it
//                    }
//            }
//        }
//    }
//}
