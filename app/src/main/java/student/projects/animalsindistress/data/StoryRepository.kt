package student.projects.animalsindistress.data

import student.projects.animalsindistress.data.models.Story

interface StoryRepository {
    suspend fun getStories(page: Int, pageSize: Int): List<Story>
    suspend fun getStoryById(storyId: String): Story?
    suspend fun toggleLike(storyId: String): Story?
    suspend fun toggleFollow(storyId: String): Story?
}


