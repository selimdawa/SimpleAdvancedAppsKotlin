package com.flatcode.simpleadvancedapps.movies.data.retrofit

import com.flatcode.simpleadvancedapps.movies.data.retrofit.api.RetrofitInstance
import com.flatcode.simpleadvancedapps.movies.models.MoviesModel
import retrofit2.Response

class RetrofitRepository {
    suspend fun getMovie(): Response<MoviesModel> {
        return RetrofitInstance.api.getPopularMovie()
    }
}