package com.example.weather_app.data.knowledge

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weather_app.model.Knowledge
import com.example.weather_app.model.News
import com.example.weather_app.model.Screens

@Dao
interface knowledgeDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(knowledge: Knowledge)
    @Update
    suspend fun update(knowledge: Knowledge)
    @Delete
    suspend fun delete(knowledge: Knowledge)
    @Query("select * from knowledge where id = :id")
    fun getKnowledge(id: Int): LiveData<Knowledge?>
    @Query("select * from knowledge order by title asc")
    fun getAllKnowledge(): LiveData<List<Knowledge>>
    @Query("select count(*) from knowledge")
    suspend fun getCountKnowledge(): Int
    @Query("SELECT * FROM knowledge WHERE title = :title")
    fun getKnowledgeByTitle(title: String): LiveData<Knowledge?>
}