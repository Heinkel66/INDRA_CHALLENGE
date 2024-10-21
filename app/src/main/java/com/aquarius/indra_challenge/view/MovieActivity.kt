package com.aquarius.indra_challenge.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquarius.indra_challenge.data.AppDatabase
import com.aquarius.indra_challenge.databinding.ActivityMovieBinding
import com.aquarius.indra_challenge.repository.MovieRepository
import com.aquarius.indra_challenge.service.RetrofitInstance
import com.aquarius.indra_challenge.util.Constants
import com.aquarius.indra_challenge.util.LoadingDialog
import com.aquarius.indra_challenge.view.adapter.MovieAdapter
import com.aquarius.indra_challenge.viewmodel.MovieViewModel
import com.aquarius.indra_challenge.viewmodel.ViewModelFactory

class MovieActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var adapter: MovieAdapter

    private lateinit var binding: ActivityMovieBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

        val movieDao = AppDatabase.getDatabase(application).movieDao()
        val movieRepository = MovieRepository(RetrofitInstance.api, movieDao)
        val factory = ViewModelFactory(null, movieRepository)

        viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        viewModel.movies.observe(this) { movieList ->
            loadingDialog.dismiss()

            if (movieList != null) {
                adapter = MovieAdapter(movieList)
                binding.recyclerViewMovies.adapter = adapter
            } else {
                Toast.makeText(this, "No se pudieron cargar las películas", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.error.observe(this) { error ->
            loadingDialog.dismiss()
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.clearError()
            }
        }
    }

    private fun initView() {
        loadingDialog = LoadingDialog(this)

        adapter = MovieAdapter(emptyList())
        binding.recyclerViewMovies.adapter = adapter
        binding.recyclerViewMovies.layoutManager = LinearLayoutManager(this)

        binding.buttonNext.setOnClickListener {
            loadingDialog.show()
            viewModel.nextPage()
        }

        binding.buttonPrevious.setOnClickListener {
            loadingDialog.show()
            viewModel.previousPage()
        }
    }
}