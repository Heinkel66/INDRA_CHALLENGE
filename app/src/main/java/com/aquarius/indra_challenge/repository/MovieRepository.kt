package com.aquarius.indra_challenge.repository

import androidx.lifecycle.LiveData
import com.aquarius.indra_challenge.data.HeaderDao
import com.aquarius.indra_challenge.data.HeaderEntity
import com.aquarius.indra_challenge.data.MovieDao
import com.aquarius.indra_challenge.data.MovieEntity
import com.aquarius.indra_challenge.service.MovieApiService

class MovieRepository(
    private val api: MovieApiService,
    private val dao: MovieDao,
    private val headerDao: HeaderDao
) {
    val movies: LiveData<List<MovieEntity>> = dao.getAllMovies()

    suspend fun fetchMovies(page: Int, apiKey: String) {
        try {
            val response = api.getUpcomingMovies(page, apiKey)

            if (response.results.isNotEmpty()) {
                dao.clearMovies()
                headerDao.clearHeaders()

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

                headerDao.insertHeader(
                    HeaderEntity(
                        1,
                        response.total_pages,
                        response.total_results,
                        response.results.size,
                        response.page
                    )
                )
            } else {
                throw Exception("No se encontraron películas en la página $page")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Error al obtener las películas: ${e.message}")
        }
    }
}
