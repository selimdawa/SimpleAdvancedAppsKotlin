package com.flatcode.simpleadvancedapps.movies.data.retrofit.api

import com.flatcode.simpleadvancedapps.movies.models.MoviesModel
import com.flatcode.simpleadvancedapps.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(Constants.POPULAR_MOVIES)
    suspend fun getPopularMovie(): Response<MoviesModel>
}