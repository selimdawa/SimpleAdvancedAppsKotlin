package com.flatcode.simpleadvancedapps.dogs.data

import com.flatcode.simpleadvancedapps.dogs.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepository @Inject constructor(
    private val apiService: ApiService,
    private val dogDao: DogDao,
) {
    fun getDogsByBreed(breed: String, isOnline: Boolean): Flow<List<String>> = flow {
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
            } catch (_: Exception) {
            }
        }
        emitAll(
            dogDao.getDogsByBreed(breed).map { entities ->
                entities.map { it.imageUrl }
            })
    }
}
