package com.example.weather_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_app.data.news.newsDB
import com.example.weather_app.data.news.newsRepository
import com.example.weather_app.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: newsRepository
    val allNews: LiveData<List<News>>
    init {
        val newsDAO = newsDB.getDatabase(application).newsDAO()
        repository = newsRepository(newsDAO)
        allNews = repository.allNews
    }

    fun getNews(id: Int): LiveData<News?> {
        return repository.getNewsStream(id)
    }
    fun getNewsCount(): LiveData<Int> {
        val countLiveData = MutableLiveData<Int>()
        viewModelScope.launch(Dispatchers.IO) {
            val count = repository.getCountNews()
            countLiveData.postValue(count)
        }
        return countLiveData
    }

    fun insert(news: News) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNews(news)
    }

    fun update(news: News) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNews(news)
    }

    fun delete(news: News) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNews(news)
    }

}
