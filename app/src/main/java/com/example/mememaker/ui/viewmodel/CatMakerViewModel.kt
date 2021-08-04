package com.example.mememaker.ui.viewmodel

import androidx.lifecycle.*
import com.example.mememaker.data.models.Cat
import com.example.mememaker.data.repo.CatRepo
import com.example.mememaker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatMakerViewModel @Inject constructor(
    private val repository: CatRepo,
    private val state: SavedStateHandle
) : ViewModel() {

    private var _catImage = MutableLiveData<Resource<Cat>>()
    val catImage: LiveData<Resource<Cat>> get() = _catImage

    var text = state.get<String>("meme_text") ?: ""
        set(value) {
            field = value
            state.set("meme_text", value)
            setShowTextAttributes()
        }

    var textSize = state.get<String>("text_size") ?: ""
        set(value) {
            field = value
            state.set("text_size", value)
        }

    var textColor = state.get<String>("text_color") ?: ""
        set(value) {
            field = value
            state.set("text_color", value)
        }

    var mediaType = state.get<MediaType>("media_type") ?: MediaType.IMAGE
        set(value) {
            field = value
            state.set("media_type", value)
        }

    var filter = state.get<String>("filter")
        set(value) {
            field = value
            state.set("filter", value)
        }


    private var _showTextAttributes = MutableLiveData(false)
    var showTextAttributes: LiveData<Boolean> = _showTextAttributes

    private fun setShowTextAttributes() {
        _showTextAttributes.value = text.isNotEmpty()
    }


    fun getImage() {
        _catImage.value = Resource.Loading
        val textSizeNum = if (textSize == "") null else textSize.toInt()
        println(mediaType)
        viewModelScope.launch(Dispatchers.IO) {
            when (mediaType) {
                MediaType.IMAGE -> {
                    if (text.isEmpty()) {
                        _catImage.postValue(repository.getCatImage(filter))
                    } else {
                        _catImage.postValue(
                            repository.getCatGifWithText(
                                filter,
                                text,
                                textColor,
                                textSizeNum
                            )
                        )
                    }
                }
                MediaType.GIF -> {
                    println("hello")
                    if (text.isEmpty()) {
                        val response = repository.getCatGif(filter)
                        _catImage.postValue(response)
                    } else {
                        _catImage.postValue(
                            repository.getCatGifWithText(
                                filter,
                                text,
                                textColor,
                                textSizeNum
                            )
                        )
                    }
                }
            }
        }
    }


    fun clearData() {
        text = ""
        textSize = ""
        textColor = ""
        filter = null
    }

    enum class MediaType {
        IMAGE, GIF
    }

}