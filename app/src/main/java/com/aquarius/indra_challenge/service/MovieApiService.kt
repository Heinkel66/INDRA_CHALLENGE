package com.aquarius.indra_challenge.service

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): MovieResponse
}