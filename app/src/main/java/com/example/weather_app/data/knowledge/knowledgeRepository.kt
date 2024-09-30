package com.example.weather_app.data.knowledge

import androidx.lifecycle.LiveData
import com.example.weather_app.data.news.newsDAO
import com.example.weather_app.model.Knowledge
import com.example.weather_app.model.News

class knowledgeRepository(private val knowledgeDAO: knowledgeDAO) {
    val allKnowledge: LiveData<List<Knowledge>> = knowledgeDAO.getAllKnowledge()
    fun getKnowledgeStream(id: Int): LiveData<Knowledge?>
    {
        return knowledgeDAO.getKnowledge(id)
    }
    suspend fun getCountKnowledge(): Int
    {
        return knowledgeDAO.getCountKnowledge()
    }
    suspend fun insertKnowledge(knowledge: Knowledge)
    {
        knowledgeDAO.insert(knowledge)
    }
    suspend fun deleteKnowledge(knowledge: Knowledge){
        knowledgeDAO.delete(knowledge)
    }
    suspend fun updateKnowledge(knowledge: Knowledge)
    {
        knowledgeDAO.update(knowledge)
    }
}