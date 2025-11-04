package student.projects.animalsindistress.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import org.json.JSONArray
import org.json.JSONObject
import student.projects.animalsindistress.data.models.BeforeAfterTag
import student.projects.animalsindistress.data.models.MediaItem
import student.projects.animalsindistress.data.models.MediaType
import student.projects.animalsindistress.data.models.Story

class FirestoreStoryRepository(private val context: Context) : StoryRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("stories_prefs", Context.MODE_PRIVATE)
    }

    // Cache parsed stories in memory
    @Volatile
    private var cachedStories: List<Story>? = null

    override suspend fun getStories(page: Int, pageSize: Int): List<Story> {
        return try {
            val all = loadAllStories()
            val from = (page * pageSize).coerceAtMost(all.size)
            val to = (from + pageSize).coerceAtMost(all.size)
            all.subList(from, to)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading stories: ${e.message}", e)
            emptyList()
        }
    }

    override suspend fun getStoryById(storyId: String): Story? {
        return try {
            loadAllStories().find { it.id == storyId }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading story $storyId: ${e.message}", e)
            null
        }
    }

    override suspend fun toggleLike(storyId: String): Story? {
        return try {
            val key = likeKey(storyId)
            val nowLiked = !(prefs.getBoolean(key, false))
            prefs.edit().putBoolean(key, nowLiked).apply()
            updateCacheFlag(storyId) { it.copy(liked = nowLiked) }
            cachedStories?.find { it.id == storyId }
        } catch (e: Exception) {
            Log.e(TAG, "Error toggling like for $storyId: ${e.message}", e)
            null
        }
    }

    override suspend fun toggleFollow(storyId: String): Story? {
        return try {
            val key = followKey(storyId)
            val nowFollowing = !(prefs.getBoolean(key, false))
            prefs.edit().putBoolean(key, nowFollowing).apply()
            updateCacheFlag(storyId) { it.copy(following = nowFollowing) }
            cachedStories?.find { it.id == storyId }
        } catch (e: Exception) {
            Log.e(TAG, "Error toggling follow for $storyId: ${e.message}", e)
            null
        }
    }

    /**
     * Load all stories from both local JSON file and Firestore, then merge them
     */
    private suspend fun loadAllStories(): List<Story> {
        cachedStories?.let { return it }
        
        return try {
            // Load local stories from JSON
            val localStories = loadLocalStories()
            Log.d(TAG, "Loaded ${localStories.size} local stories from JSON")
            
            // Load Firestore stories
            Log.d(TAG, "Loading stories from Firestore...")
            val snapshot = firestore.collection("stories")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val firestoreStories = snapshot.documents.mapNotNull { doc ->
                try {
                    val story = doc.toObject(Story::class.java)
                    if (story != null) {
                        // Apply user preferences for liked/following
                        val liked = prefs.getBoolean(likeKey(story.id), false)
                        val following = prefs.getBoolean(followKey(story.id), false)
                        story.copy(liked = liked, following = following)
                    } else {
                        Log.w(TAG, "Failed to parse story document: ${doc.id}")
                        null
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing story ${doc.id}", e)
                    null
                }
            }
            
            Log.d(TAG, "Loaded ${firestoreStories.size} stories from Firestore")
            
            // Merge both sources - Firestore stories + local stories
            // Remove duplicates by ID (Firestore takes precedence)
            val firestoreIds = firestoreStories.map { it.id }.toSet()
            val uniqueLocalStories = localStories.filter { it.id !in firestoreIds }
            val allStories = firestoreStories + uniqueLocalStories
            
            Log.d(TAG, "Total stories after merge: ${allStories.size} (${firestoreStories.size} from Firestore, ${uniqueLocalStories.size} from local)")
            
            // Prioritize followed stories first, then by timestamp descending
            val sorted = allStories.sortedWith(
                compareByDescending<Story> { it.following }
                    .thenByDescending { it.timestamp }
            )
            cachedStories = sorted
            sorted
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load stories", e)
            // Fallback to local stories only if Firestore fails
            loadLocalStories()
        }
    }

    /**
     * Load stories from local JSON file (original 3 hardcoded stories)
     */
    private fun loadLocalStories(): List<Story> {
        return try {
            val json = context.assets.open("stories.json").bufferedReader().use { it.readText() }
            val root = JSONObject(json)
            val arr = root.optJSONArray("stories") ?: JSONArray()
            val parsed = buildList {
                for (i in 0 until arr.length()) {
                    try {
                        val obj = arr.getJSONObject(i)
                        val id = obj.optString("id", "story_$i")
                        val title = obj.optString("title", "Untitled Story")
                        val summary = obj.optString("summary", "")
                        val fullContent = obj.optString("fullContent", summary)
                        val adoptedUpdate = obj.optString("adoptedUpdate", null).takeIf { it.isNotBlank() }
                        val timestamp = obj.optLong("timestamp", System.currentTimeMillis())
                        
                        val mediaArr = obj.optJSONArray("media") ?: JSONArray()
                        val media = mutableListOf<MediaItem>()
                        for (j in 0 until mediaArr.length()) {
                            try {
                                val m = mediaArr.getJSONObject(j)
                                val type = when (m.optString("type").uppercase()) {
                                    "VIDEO" -> MediaType.VIDEO
                                    else -> MediaType.IMAGE
                                }
                                val path = m.optString("urlOrRes", "")
                                if (path.isBlank()) continue
                                
                                val thumb = m.optString("thumbnail", "").takeIf { it.isNotBlank() }
                                val tag = when (m.optString("beforeAfterTag").uppercase()) {
                                    "BEFORE" -> BeforeAfterTag.BEFORE
                                    "AFTER" -> BeforeAfterTag.AFTER
                                    else -> BeforeAfterTag.NONE
                                }
                                media.add(MediaItem(type = type, urlOrPath = path, thumbnailPath = thumb, beforeAfterTag = tag))
                            } catch (e: Exception) {
                                Log.w(TAG, "Failed to parse media item $j in story $id", e)
                            }
                        }
                        
                        val liked = prefs.getBoolean(likeKey(id), false)
                        val following = prefs.getBoolean(followKey(id), false)
                        add(Story(
                            id = id, 
                            title = title, 
                            summary = summary, 
                            fullContent = fullContent,
                            adoptedUpdate = adoptedUpdate, 
                            media = media, 
                            liked = liked, 
                            following = following,
                            timestamp = timestamp
                        ))
                    } catch (e: Exception) {
                        Log.w(TAG, "Failed to parse story at index $i", e)
                    }
                }
            }
            parsed
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load local stories from JSON", e)
            emptyList()
        }
    }

    /**
     * Clear cache to force reload from Firestore. Useful for refresh functionality.
     */
    fun clearCache() {
        cachedStories = null
    }

    private fun updateCacheFlag(storyId: String, update: (Story) -> Story) {
        cachedStories = cachedStories?.map { if (it.id == storyId) update(it) else it }
    }

    private fun likeKey(id: String) = "like_$id"
    private fun followKey(id: String) = "follow_$id"
    
    companion object {
        private const val TAG = "FirestoreStoryRepository"
    }
}
