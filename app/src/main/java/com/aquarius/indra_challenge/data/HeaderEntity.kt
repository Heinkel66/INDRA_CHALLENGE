package com.aquarius.indra_challenge.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "header")
data class HeaderEntity(
    @PrimaryKey val id: Int,
    val total_pages: Int,
    val total_results: Int,
    val total_results_per_page: Int,
    val page: Int
)