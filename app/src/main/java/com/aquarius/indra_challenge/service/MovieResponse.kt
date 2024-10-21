package com.aquarius.indra_challenge.service

import com.aquarius.indra_challenge.domain.Movie

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)