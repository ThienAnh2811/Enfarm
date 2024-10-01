package com.example.weather_app.data.news
import android.content.Context
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.example.weather_app.model.FirebaseNews
import com.example.weather_app.model.News
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class FirebaseNewsRepository {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val newsReference: DatabaseReference = database.getReference("News")

    fun byteArrayToBase64(byteArray: ByteArray): String {
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun base64ToByteArray(base64String: String): ByteArray {
        return Base64.decode(base64String, Base64.DEFAULT)
    }

    fun getAllNewsFromFirebase(callback: (List<News>) -> Unit) {
        newsReference.get()
            .addOnSuccessListener { dataSnapshot ->
                val newsList = mutableListOf<News>()
                for (snapshot in dataSnapshot.children) {
                    val news = snapshot.getValue(News::class.java)
                    news?.let {
                        val byteArrayThumbnail = base64ToByteArray(it.thumbnailBase64)
                        newsList.add(it.copy(thumbnail = byteArrayThumbnail))
                    }
                }
                callback(newsList)
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseSync", "Error getting news from Firebase: ${exception.message}", exception)
            }
    }

    fun insertOrUpdateNewsInFirebase(news: News, context: Context, onSuccess: () -> Unit) {
        val base64Thumbnail = byteArrayToBase64(news.thumbnail)

        val query = newsReference.orderByChild("title").equalTo(news.title)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(context, "News with this title already exists", Toast.LENGTH_SHORT).show()
                } else {
                    val firebaseNews = FirebaseNews(
                        id = news.id,
                        title = news.title,
                        desc = news.desc,
                        thumbnailBase64 = base64Thumbnail
                    )

                    val newsRef = if (news.id == 0) newsReference.push() else newsReference.child(news.id.toString())

                    newsRef.setValue(firebaseNews)
                        .addOnSuccessListener {
                            Log.d("FirebaseSync", "News synced successfully with ID: ${newsRef.key}")
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



    fun deleteNewsFromFirebase(newstitle: String) {
        val query = newsReference.orderByChild("title").equalTo(newstitle)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (newsSnapshot in dataSnapshot.children) {
                        val newsKey = newsSnapshot.key
                        if (newsKey != null) {
                            newsReference.child(newsKey).removeValue()
                                .addOnSuccessListener {
                                    Log.d("FirebaseSync", "News deleted successfully with title: $newstitle")
                                }
                                .addOnFailureListener { e ->
                                    Log.e("FirebaseSync", "Error deleting news: ${e.message}", e)
                                }
                        }
                    }
                } else {
                    Log.d("FirebaseSync", "No news found with the title: $newstitle")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("FirebaseSync", "Error querying news by title: ${databaseError.message}")
            }
        })
    }
    fun getNewsByTitle(title: String, callback: (News?) -> Unit) {
        val query = newsReference.orderByChild("title").equalTo(title)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (newsSnapshot in dataSnapshot.children) {
                        val news = newsSnapshot.getValue(News::class.java)
                        news?.let {
                            val byteArrayThumbnail = base64ToByteArray(it.thumbnailBase64)
                            callback(it.copy(thumbnail = byteArrayThumbnail))
                            return
                        }
                    }
                } else {
                    Log.d("FirebaseSync", "No news found with the title: $title")
                    callback(null)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("FirebaseSync", "Error querying news by title: ${databaseError.message}")
                callback(null)
            }
        })
    }

}
