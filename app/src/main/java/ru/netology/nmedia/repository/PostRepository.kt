package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post
import java.lang.Exception

interface PostRepository {
    fun getAll(callback: Callback<List<Post>>)
    fun likeById(id: Long): Post
    fun save(post: Post)
    fun removeById(id: Long)
    fun getById(id: Long): Post

    interface Callback<T> {
        fun onSuccess(result: T)
        fun onError(exception: Exception)
    }
}
