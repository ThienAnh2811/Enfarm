package com.example.weather_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_app.data.news.FirebaseNewsRepository
import com.example.weather_app.data.news.newsDB
import com.example.weather_app.data.news.newsRepository
import com.example.weather_app.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: newsRepository
    private val firebaseRepository: FirebaseNewsRepository = FirebaseNewsRepository()
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
        try {
            // Insert into the local Room database
            repository.insertNews(news)

            // Insert into Firebase with Base64-encoded thumbnail
            firebaseRepository.insertOrUpdateNewsInFirebase(news)
        } catch (e: Exception) {
            Log.e("NewsViewModel", "Error inserting news", e)
        }
    }

    fun update(news: News) = viewModelScope.launch(Dispatchers.IO) {
        try {
            // Update in the local Room database
            repository.updateNews(news)

            // Update in Firebase
            firebaseRepository.insertOrUpdateNewsInFirebase(news)
        } catch (e: Exception) {
            Log.e("NewsViewModel", "Error updating news", e)
        }
    }

    fun delete(news: News) = viewModelScope.launch(Dispatchers.IO) {
        try {
            // Delete from local Room database
            repository.deleteNews(news)

            // Delete from Firebase
            firebaseRepository.deleteNewsFromFirebase(news.id)
        } catch (e: Exception) {
            Log.e("NewsViewModel", "Error deleting news", e)
        }
    }
}
