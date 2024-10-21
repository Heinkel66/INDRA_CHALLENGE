package com.aquarius.indra_challenge.service

import com.aquarius.indra_challenge.domain.Movie

data class MovieResponse(
    val results: List<Movie>
)