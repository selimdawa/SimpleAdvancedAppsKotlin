package com.flatcode.simpleadvancedapps.dogs.data

import com.flatcode.simpleadvancedapps.dogs.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepository @Inject constructor(
    private val apiService: ApiService,
    private val dogDao: DogDao,
) {
    fun getDogsByBreed(breed: String, isOnline: Boolean): Flow<List<String>> = flow {
        val localData = dogDao.getDogsByBreedOnce(breed).map { it.imageUrl }
        if (localData.isNotEmpty()) {
            emit(localData)
        }

        if (isOnline) {
            try {
                val lowercaseBreed = breed.lowercase()
                val response = if (" " in lowercaseBreed) {
                    val parts = lowercaseBreed.split(" ")
                    apiService.getSubBreedImages(parts[0], parts[1])
                } else {
                    apiService.getBreedImages(lowercaseBreed)
                }

                val entities = response.images.map { DogEntity(it, breed) }
                dogDao.deleteDogsByBreed(breed)
                dogDao.insertDogs(entities)

                emit(entities.map { it.imageUrl })
            } catch (e: Exception) {
                if (localData.isEmpty()) throw e
            }
        } else if (localData.isEmpty()) {
            throw Exception("No internet and no cached data")
        }
    }
}