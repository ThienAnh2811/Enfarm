package com.example.weather_app.data.news
import android.util.Base64
import android.util.Log
import com.example.weather_app.model.FirebaseNews
import com.example.weather_app.model.News
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class FirebaseNewsRepository {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val newsReference: DatabaseReference = database.getReference("News")

    // Convert ByteArray to Base64 String
    fun byteArrayToBase64(byteArray: ByteArray): String {
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    // Convert Base64 String to ByteArray
    fun base64ToByteArray(base64String: String): ByteArray {
        return Base64.decode(base64String, Base64.DEFAULT)
    }

    // Get all news from Firebase and decode thumbnail from Base64 to ByteArray
    fun getAllNewsFromFirebase(callback: (List<News>) -> Unit) {
        newsReference.get()
            .addOnSuccessListener { dataSnapshot ->
                val newsList = mutableListOf<News>()
                for (snapshot in dataSnapshot.children) {
                    val firebaseNews = snapshot.getValue(FirebaseNews::class.java)
                    firebaseNews?.let {
                        val byteArrayThumbnail = base64ToByteArray(it.thumbnailBase64)
                        val news = News(
                            id = it.id,
                            title = it.title,
                            desc = it.desc,
                            thumbnail = byteArrayThumbnail,
                            thumbnailBase64 = it.thumbnailBase64
                        )
                        newsList.add(news)
                    }
                }
                callback(newsList)
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseSync", "Error getting news from Firebase: ${exception.message}", exception)
            }
    }

    // Insert or update news in Firebase with detailed logging
    fun insertOrUpdateNewsInFirebase(news: News) {
        val base64Thumbnail = byteArrayToBase64(news.thumbnail)

        val firebaseNews = FirebaseNews(
            id = news.id,
            title = news.title,
            desc = news.desc,
            thumbnailBase64 = base64Thumbnail
        )


        // If ID is 0, generate a new key in Firebase
        val newsRef = if (news.id == 0) newsReference.push() else newsReference.child(news.id.toString())

        // Write the news to Firebase
        newsRef.setValue(firebaseNews)

            .addOnSuccessListener {
                Log.d("FirebaseSync", "News synced successfully with ID: ${newsRef.key}")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseSync", "Error syncing news: ${e.message}", e)
            }
    }

    fun deleteNewsFromFirebase(newsId: Int) {
        val newsRef = newsReference.child(newsId.toString())
        newsRef.removeValue()
            .addOnSuccessListener {
                Log.d("FirebaseSync", "News deleted successfully with ID: $newsId")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseSync", "Error deleting news: ${e.message}", e)
            }
    }
}
