package ru.netology.nmedia.repository

import retrofit2.*
import ru.netology.nmedia.api.ApiService
import ru.netology.nmedia.dto.Post
import java.io.IOException


class PostRepositoryImpl : PostRepository {
    override fun getAll() {
        return ApiService.service.getAll()
            .execute()
            .let { it.body() ?: throw RuntimeException("body is null") }
    }

    override fun getAllAsync(callback: PostRepository.Callback<List<Post>>) {
        ApiService.service.getAll()
            .enqueue(object : Callback<List<Post>> {
                override fun onResponse(
                    call: Call<List<Post>>,
                    response: Response<List<Post>>
                ) {
                    if (!response.isSuccessful) {
                        callback.onError(RuntimeException(response.errorBody()?.string()))
                        return
                    }
                    val body = response.body() ?: throw RuntimeException("body is null")
                    try {
                        callback.onSuccess(body)
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    callback.onError(Exception(t))
                }

            }
            )
    }


    override fun likeById(id: Long, likedByMe: Boolean, callback: PostRepository.Callback<Post>) {

    }

    override fun save(post: Post, callback: PostRepository.Callback<Post>) {
        ApiService.service
            .save(post)
            .execute()
    }

    override fun removeById(id: Long, callback: PostRepository.Callback<Boolean>) {
        ApiService.service
            .deleteById(id)
            .execute()
    }
}
