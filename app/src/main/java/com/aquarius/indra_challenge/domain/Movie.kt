package com.aquarius.indra_challenge.domain

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val vote_average: Float,
    val release_date: String
)
