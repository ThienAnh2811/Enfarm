package com.example.weather_app.data

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import com.example.weather_app.model.News

//class OfflineNewsRepository(private val newsDAO: newsDAO): newsRepository {
//    override fun getAllNewsStream(): LiveData<List<News>> = newsDAO.getAllNews()
//    override fun getNewsStream(id: Int): LiveData<News?> = newsDAO.getNews(id)
//    override suspend fun insertNews(news: News) = newsDAO.insert(news)
//    override suspend fun deleteNews(news: News) = newsDAO.delete(news)
//    override suspend fun updateNews(news: News) = newsDAO.update(news)
//
//}