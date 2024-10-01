package com.example.weather_app.data.news

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weather_app.model.News

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
    @Query("select count(*) from news")
    suspend fun getCountNews(): Int
    @Query("SELECT * FROM news ORDER BY title DESC LIMIT 5")
    fun getLatestNews(): LiveData<List<News>>
    @Query("SELECT * FROM news WHERE title = :title")
    fun getNewsByTitle(title: String): LiveData<News?>
}