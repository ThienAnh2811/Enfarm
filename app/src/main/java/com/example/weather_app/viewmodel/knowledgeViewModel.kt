package com.example.weather_app.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_app.data.knowledge.FirebaseKnowledgeRepository
import com.example.weather_app.data.knowledge.knowledgeRepository
import com.example.weather_app.data.news.FirebaseNewsRepository
import com.example.weather_app.data.news.newsDB
import com.example.weather_app.data.news.newsRepository
import com.example.weather_app.model.Knowledge
import com.example.weather_app.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KnowledgeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: knowledgeRepository
    private val firebaseRepository: FirebaseKnowledgeRepository = FirebaseKnowledgeRepository()
    val allKnowledge: LiveData<List<Knowledge>>

    init {
        val knowledgeDAO = newsDB.getDatabase(application).knowledgeDAO()
        repository = knowledgeRepository(knowledgeDAO)
        allKnowledge = repository.allKnowledge
    }

    fun getKnowledge(id: Int): LiveData<Knowledge?> {
        return repository.getKnowledgeStream(id)
    }

    fun getKnowledgeCount(): LiveData<Int> {
        val countLiveData = MutableLiveData<Int>()
        viewModelScope.launch(Dispatchers.IO) {
            val count = repository.getCountKnowledge()
            countLiveData.postValue(count)
        }
        return countLiveData
    }

    fun insert(knowledge: Knowledge, context: Context, onSuccess: () -> Unit) = viewModelScope.launch(
        Dispatchers.IO) {
        try {
            repository.insertKnowledge(knowledge)
            firebaseRepository.insertOrUpdateKnowledgeInFirebase(knowledge, context) {
                onSuccess()
            }
        } catch (e: Exception) {
            Log.e("NewsViewModel", "Error inserting news", e)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error inserting news", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun update(knowledge: Knowledge, context: Context, onSuccess: () -> Unit) = viewModelScope.launch(
        Dispatchers.IO) {
        try {
            repository.updateKnowledge(knowledge)
            firebaseRepository.insertOrUpdateKnowledgeInFirebase(knowledge, context) {
                onSuccess()
            }
        } catch (e: Exception) {
            Log.e("NewsViewModel", "Error updating news", e)
        }
    }

    fun delete(knowledge: Knowledge) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.deleteKnowledge(knowledge)
            firebaseRepository.deleteKnowledgeFromFirebase(knowledge.title)
        } catch (e: Exception) {
            Log.e("NewsViewModel", "Error deleting news", e)
        }
    }
}
