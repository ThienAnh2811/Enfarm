package com.example.weather_app.data

import androidx.lifecycle.LiveData
import com.example.weather_app.model.News

interface newsRepository {
    fun getAllNewsStream(): LiveData<List<News>>
    fun getNewsStream(id: Int): LiveData<News?>
    suspend fun insertNews(news: News)
    suspend fun deleteNews(news: News)
    suspend fun updateNews(news: News)
}