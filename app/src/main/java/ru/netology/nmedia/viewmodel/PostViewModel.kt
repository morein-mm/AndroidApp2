package ru.netology.nmedia.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.R
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.util.SingleLiveEvent

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    authorAvatar = "",
    likedByMe = false,
    likes = 0,
    published = "",
    attachment = null
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Post>()
    val postCreated: LiveData<Post>
        get() = _postCreated
    private val _error = SingleLiveEvent<Exception>()
    val error: LiveData<Exception>
        get() = _error

    init {
        loadPosts()
    }

    fun loadPosts() {
        // Начинаем загрузку
        _data.value = FeedModel(loading = true)
        // Данные успешно получены
        repository.getAllAsync(
            object : PostRepository.Callback<List<Post>> {
                override fun onSuccess(result: List<Post>) {
                    _data.value = FeedModel(posts = result, empty = result.isEmpty())
                }

                override fun onError(exception: Exception) {
                    _data.value = FeedModel(error = true)
                }
            }
        )
    }

    fun save() {
        edited.value?.let {

            repository.saveAsync(
                it,
                object : PostRepository.Callback<Post> {
                    override fun onSuccess(result: Post) {
                        _postCreated.postValue(result)
                        edited.postValue(empty)
                    }

                    override fun onError(exception: Exception) {
                        _error.value = exception
                    }
                }
            )
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) {
        val old = _data.value?.posts.orEmpty()
        val likedByMe = _data.value?.posts?.find { it.id == id }?.likedByMe ?: return

        repository.likeByIdAsync(
            id,
            likedByMe,
            object : PostRepository.Callback<Post> {
                override fun onSuccess(result: Post) {
                    _data.value = _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .map { if (it.id == id) result else it })
                }

                override fun onError(exception: Exception) {
                    _data.value = _data.value?.copy(posts = old)
                    _error.value = exception
                }
            }
        )
    }

    fun removeById(id: Long) {

        // Оптимистичная модель
        val old = _data.value?.posts.orEmpty()
        _data.postValue(
            _data.value?.copy(posts = _data.value?.posts.orEmpty()
                .filter { it.id != id }
            )
        )

        repository.removeByIdAsync(
            id,
            object : PostRepository.Callback<Unit> {
                override fun onSuccess(result: Unit) {}

                override fun onError(exception: java.lang.Exception) {
                    _data.postValue(_data.value?.copy(posts = old))
                    _error.value = exception
                }

            }
        )
    }
}