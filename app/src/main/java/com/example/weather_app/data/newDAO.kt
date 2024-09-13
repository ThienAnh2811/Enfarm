package com.example.weather_app.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weather_app.model.News
import java.util.concurrent.Flow

@Dao
interface newsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(news: News)
    @Update
    suspend fun update(news: News)
    @Delete
    suspend fun delete(news: News)
    @Query("select * from news where id = :id")
    fun getNews(id: Int): LiveData<News?>
    @Query("select * from news order by title asc")
    fun getAllNews(): LiveData<List<News>>
}