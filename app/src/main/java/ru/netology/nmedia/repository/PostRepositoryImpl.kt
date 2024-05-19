package ru.netology.nmedia.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.netology.nmedia.api.ApiService
import ru.netology.nmedia.dto.Post


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
                    try {
                        if (!response.isSuccessful) {
                            callback.onError(RuntimeException(response.errorBody()?.string()))
                            return
                        }
                        val body = response.body() ?: throw RuntimeException("body is null")

                        callback.onSuccess(body)
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    callback.onError(Exception(t))
                }

            })
    }


    override fun likeById(id: Long, likedByMe: Boolean) {
        if (likedByMe) {
            ApiService.service
                .unlikeById(id)
                .execute()
        } else {
            ApiService.service
                .likeById(id)
                .execute()
        }
    }

    override fun likeByIdAsync(
        id: Long,
        likedByMe: Boolean,
        callback: PostRepository.Callback<Post>
    ) {
        if (likedByMe) {
            ApiService.service.unlikeById(id)
                .enqueue(object : Callback<Post> {
                    override fun onResponse(
                        call: Call<Post>,
                        response: Response<Post>
                    ) {
                        try {
                            if (!response.isSuccessful) {
                                callback.onError(RuntimeException(response.errorBody()?.string()))
                                return
                            }
                            val body = response.body() ?: throw RuntimeException("body is null")
                            callback.onSuccess(body)
                        } catch (e: Exception) {
                            callback.onError(e)
                        }
                    }

                    override fun onFailure(call: Call<Post>, t: Throwable) {
                        callback.onError(Exception(t))
                    }

                })

        } else {

            ApiService.service.likeById(id)
                .enqueue(object : Callback<Post> {
                    override fun onResponse(
                        call: Call<Post>,
                        response: Response<Post>
                    ) {
                        try {
                            if (!response.isSuccessful) {
                                callback.onError(RuntimeException(response.errorBody()?.string()))
                                return
                            }
                            val body = response.body() ?: throw RuntimeException("body is null")

                            callback.onSuccess(body)
                        } catch (e: Exception) {
                            callback.onError(e)
                        }
                    }

                    override fun onFailure(call: Call<Post>, t: Throwable) {
                        callback.onError(Exception(t))
                    }

                })
        }
    }

    override fun save(post: Post) {
        ApiService.service
            .save(post)
            .execute()
    }

    override fun saveAsync(post: Post, callback: PostRepository.Callback<Post>) {
        ApiService.service.save(post)
            .enqueue(object : Callback<Post> {
                override fun onResponse(
                    call: Call<Post>,
                    response: Response<Post>
                ) {
                    try {
                        if (!response.isSuccessful) {
                            callback.onError(RuntimeException(response.errorBody()?.string()))
                            return
                        }
                        val body = response.body() ?: throw RuntimeException("body is null")
                        callback.onSuccess(body)
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(Exception(t))
                }

            })
    }

    override fun removeById(id: Long) {
        ApiService.service
            .deleteById(id)
            .execute()
    }

    override fun removeByIdAsync(id: Long, callback: PostRepository.Callback<Unit>) {
        ApiService.service.deleteById(id)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(
                    call: Call<Unit>,
                    response: Response<Unit>
                ) {
                    try {
                        if (!response.isSuccessful) {
                            callback.onError(RuntimeException(response.errorBody()?.string()))
                            return
                        }
                        val body = response.body() ?: throw RuntimeException("body is null")
                        callback.onSuccess(body)
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    callback.onError(Exception(t))
                }

            })
    }
}
