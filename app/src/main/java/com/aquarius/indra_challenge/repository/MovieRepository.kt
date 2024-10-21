package com.aquarius.indra_challenge.repository

import androidx.lifecycle.LiveData
import com.aquarius.indra_challenge.data.MovieDao
import com.aquarius.indra_challenge.data.MovieEntity
import com.aquarius.indra_challenge.service.MovieApiService

class MovieRepository(private val api: MovieApiService, private val dao: MovieDao) {
    val movies: LiveData<List<MovieEntity>> = dao.getAllMovies()

    suspend fun fetchMovies(page: Int, apiKey: String) {
        try {
            val response = api.getUpcomingMovies(page, apiKey)

            if (response.results.isNotEmpty()) {
                dao.clearMovies()

                dao.insertMovies(response.results.map { movie ->
                    MovieEntity(
                        movie.id,
                        movie.title,
                        movie.overview,
                        movie.poster_path,
                        movie.vote_average,
                        movie.release_date
                    )
                })
            } else {
                throw Exception("No se encontraron películas en la página $page")
            }
        } catch (e: Exception) {
            throw Exception("Error al obtener las películas: ${e.message}")
        }
    }
}
