package com.example.weather_app.data.news

import androidx.lifecycle.LiveData
import com.example.weather_app.data.news.newsDAO
import com.example.weather_app.model.News

class newsRepository(private val newsDAO: newsDAO) {
    val allNews: LiveData<List<News>> = newsDAO.getAllNews()
    fun getNewsStream(id: Int): LiveData<News?>
    {
        return newsDAO.getNews(id)
    }
    suspend fun getCountNews(): Int
    {
        return newsDAO.getCountNews()
    }
    suspend fun insertNews(news: News)
    {
        newsDAO.insert(news)
    }
    suspend fun deleteNews(news: News){
        newsDAO.delete(news)
    }
    suspend fun updateNews(news: News)
    {
        newsDAO.update(news)
    }
}