package com.aquarius.indra_challenge.repository

import com.aquarius.indra_challenge.data.MovieDao
import com.aquarius.indra_challenge.data.MovieEntity
import com.aquarius.indra_challenge.service.MovieApiService

class MovieRepository(private val api: MovieApiService, private val dao: MovieDao) {
    val movies = dao.getAllMovies()

    suspend fun fetchMovies(page: Int, apiKey: String) {
        val response = api.getUpcomingMovies(page, apiKey)
        dao.insertMovies(response.results.map { movie ->
            MovieEntity(
                movie.id, movie.title, movie.overview,
                movie.poster_path, movie.vote_average, movie.release_date
            )
        })
    }
}
