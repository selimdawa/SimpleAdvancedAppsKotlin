package com.flatcode.simpleadvancedapps.rickAndMorty.data.base

import com.flatcode.simpleadvancedapps.rickAndMorty.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class BaseRepository {

    protected fun <T> doRequest(result: suspend () -> Response<T>) = flow {
        emit(Resource.Loading())
        try {
            val response = result()
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }.flowOn(Dispatchers.IO)

    protected fun <Local, Remote> doRequestWithCache(
        local: () -> Flow<Local>,
        remote: suspend () -> Response<Remote>,
        save: suspend (Remote) -> Unit
    ) = flow {
        emit(Resource.Loading())
        val cache = local().first()
        if (cache is List<*> && cache.isNotEmpty()) {
            emit(Resource.Success(cache as Local))
        }
        try {
            val response = remote()
            if (response.isSuccessful && response.body() != null) {
                save(response.body()!!)
                emitAll(local().map { Resource.Success(it) })
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }.flowOn(Dispatchers.IO)
}