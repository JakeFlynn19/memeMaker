package com.example.mememaker.data.remote

import com.example.mememaker.data.models.Cat
import retrofit2.Response
import javax.inject.Inject

class CatManager @Inject constructor(
    private val service: CatService
) {


    suspend fun getCatImage(filter: String?): Response<Cat> {
        return service.getCatImage(filter)
    }

    suspend fun getCatImageWithText(
        filter: String?,
        text: String,
        color: String?,
        size: Int?
    ): Response<Cat> {
        return service.getCatImageWithText(filter = filter, text = text, color = color, size = size)
    }

    suspend fun getCatGif(filter: String?): Response<Cat> {
        return service.getCatGif(filter)
    }

    suspend fun getCatGifWithText(
        filter: String?,
        text: String,
        color: String?,
        size: Int?
    ): Response<Cat> {
        return service.getCatGifWithText(filter = filter, text = text, color = color, size = size)
    }
}
