package com.aquarius.indra_challenge.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aquarius.indra_challenge.data.MovieEntity
import com.aquarius.indra_challenge.databinding.ItemMovieBinding
import com.bumptech.glide.Glide

class MovieAdapter(private val movieList: List<MovieEntity>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    inner class MovieViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]

        // Asignamos los datos a las vistas a través del binding
        holder.binding.textViewTitle.text = movie.title
        holder.binding.textViewOverview.text = movie.overview
        holder.binding.textViewVoteAverage.text = "Calificación: ${movie.vote_average}"
        holder.binding.textViewReleaseDate.text = "Estreno: ${movie.release_date}"

        // Cargar la imagen del poster usando Glide
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
            .into(holder.binding.imageViewPoster)
    }

    override fun getItemCount(): Int = movieList.size
}