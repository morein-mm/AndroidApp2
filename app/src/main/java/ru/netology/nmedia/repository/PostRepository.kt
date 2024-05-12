package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post
import java.lang.Exception

interface PostRepository {
    fun getAll()
    fun getAllAsync(callback: Callback<List<Post>>)
    fun likeById(id: Long, likedByMe: Boolean, callback: Callback<Post>)
    fun save(post: Post, callback: Callback<Post>)
    fun removeById(id: Long, callback: Callback<Boolean>)

    interface Callback<T> {
        fun onSuccess(result: T)
        fun onError(exception: Exception)
    }


}
