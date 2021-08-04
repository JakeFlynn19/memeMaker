package com.example.mememaker.data.repo

import com.example.mememaker.data.models.Cat
import com.example.mememaker.data.remote.CatManager
import com.example.mememaker.utils.Resource
import java.lang.Exception
import javax.inject.Inject

class CatRepo @Inject constructor(
    private val catManager: CatManager
) {

    suspend fun getCatImage(filter: String?): Resource<Cat> {
        return try {
            val catResponse = catManager.getCatImage(filter)
            if (catResponse.isSuccessful && catResponse.body() != null) {
                Resource.Success(catResponse.body()!!)
            } else {
                Resource.Error(null, "No animal found")
            }
        } catch (ex: Exception) {
            Resource.Error(ex, "Unexpected Error")
        }
    }

    suspend fun getCatImageWithText(
        filter: String?,
        text: String,
        color: String?,
        size: Int?
    ): Resource<Cat> {
        return try {
            val catResponse = catManager.getCatImageWithText(filter, text, color, size)
            if (catResponse.isSuccessful && catResponse.body() != null) {
                Resource.Success(catResponse.body()!!)
            } else {
                Resource.Error(null, "No animal found")
            }
        } catch (ex: Exception) {
            Resource.Error(ex, "Unexpected Error")
        }
    }

    suspend fun getCatGif(filter: String?): Resource<Cat> {
        return try {
            val catResponse = catManager.getCatGif(filter)
            if (catResponse.isSuccessful && catResponse.body() != null) {
                Resource.Success(catResponse.body()!!)
            } else {
                Resource.Error(null, "No animal found")
            }
        } catch (ex: Exception) {
            Resource.Error(ex, "Unexpected Error")
        }
    }

    suspend fun getCatGifWithText(
        filter: String?,
        text: String,
        color: String?,
        size: Int?
    ): Resource<Cat> {
        return try {
            val catResponse = catManager.getCatGifWithText(filter, text, color, size)
            if (catResponse.isSuccessful && catResponse.body() != null) {
                Resource.Success(catResponse.body()!!)
            } else {
                Resource.Error(null, "No animal found")
            }
        } catch (ex: Exception) {
            Resource.Error(ex, "Unexpected Error")
        }
    }
}

