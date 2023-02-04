package com.flatcode.simpleadvancedapps.movies.data.retrofit.api

import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.movies.models.MoviesModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(DATA.popular_MOVIES)
    suspend fun getPopularMovie(): Response<MoviesModel>
}