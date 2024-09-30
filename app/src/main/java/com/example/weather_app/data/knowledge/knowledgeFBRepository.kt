package com.example.weather_app.data.knowledge

import android.content.Context
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.example.weather_app.model.FirebaseKnowledge
import com.example.weather_app.model.FirebaseNews
import com.example.weather_app.model.Knowledge
import com.example.weather_app.model.News
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseKnowledgeRepository {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val knowledgeReference: DatabaseReference = database.getReference("Knowledge")

    fun byteArrayToBase64(byteArray: ByteArray): String {
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun base64ToByteArray(base64String: String): ByteArray {
        return Base64.decode(base64String, Base64.DEFAULT)
    }

    fun getAllKnowledgeFromFirebase(callback: (List<Knowledge>) -> Unit) {
        knowledgeReference.get()
            .addOnSuccessListener { dataSnapshot ->
                val KnowledgeList = mutableListOf<Knowledge>()
                for (snapshot in dataSnapshot.children) {
                    val news = snapshot.getValue(Knowledge::class.java)
                    news?.let {
                        val byteArrayThumbnail = base64ToByteArray(it.thumbnailBase64)
                        KnowledgeList.add(it.copy(thumbnail = byteArrayThumbnail))
                    }
                }
                callback(KnowledgeList)
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseSync", "Error getting news from Firebase: ${exception.message}", exception)
            }
    }

    fun insertOrUpdateKnowledgeInFirebase(knowledge: Knowledge, context: Context, onSuccess: () -> Unit) {
        val base64Thumbnail = byteArrayToBase64(knowledge.thumbnail)

        val query = knowledgeReference.orderByChild("title").equalTo(knowledge.title)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(context, "News with this title already exists", Toast.LENGTH_SHORT).show()
                } else {
                    val firebaseknowledge = FirebaseKnowledge(
                        id = knowledge.id,
                        title = knowledge.title,
                        desc = knowledge.desc,
                        thumbnailBase64 = base64Thumbnail
                    )

                    val knlRef = if (knowledge.id == 0) knowledgeReference.push() else knowledgeReference.child(knowledge.id.toString())

                    knlRef.setValue(firebaseknowledge)
                        .addOnSuccessListener {
                            Log.d("FirebaseSync", "Knowledge synced successfully with ID: ${knlRef.key}")
                            // Call the success callback to notify the insertion was successful
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            Log.e("FirebaseSync", "Error syncing news: ${e.message}", e)
                        }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("FirebaseSync", "Error querying news by title: ${databaseError.message}")
            }
        })
    }



    fun deleteKnowledgeFromFirebase(knowledgestitle: String) {
        val query = knowledgeReference.orderByChild("title").equalTo(knowledgestitle)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (newsSnapshot in dataSnapshot.children) {
                        val newsKey = newsSnapshot.key
                        if (newsKey != null) {
                            knowledgeReference.child(newsKey).removeValue()
                                .addOnSuccessListener {
                                    Log.d("FirebaseSync", "Knowledge deleted successfully with title: $knowledgestitle")
                                }
                                .addOnFailureListener { e ->
                                    Log.e("FirebaseSync", "Error deleting news: ${e.message}", e)
                                }
                        }
                    }
                } else {
                    Log.d("FirebaseSync", "No news found with the title: $knowledgestitle")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("FirebaseSync", "Error querying news by title: ${databaseError.message}")
            }
        })
    }
}
