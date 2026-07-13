package com.flatcode.simpleadvancedapps.movies.data.retrofit

import com.flatcode.simpleadvancedapps.movies.data.retrofit.api.ApiService
import com.flatcode.simpleadvancedapps.movies.models.MoviesModel
import retrofit2.Response
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getMovie(): Response<MoviesModel> {
        return apiService.getPopularMovie()
    }
}