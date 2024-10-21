package com.aquarius.indra_challenge.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.aquarius.indra_challenge.data.AppDatabase
import com.aquarius.indra_challenge.databinding.ActivityLoginBinding
import com.aquarius.indra_challenge.repository.UserRepository
import com.aquarius.indra_challenge.util.LoadingDialog
import com.aquarius.indra_challenge.viewmodel.LoginViewModel
import com.aquarius.indra_challenge.viewmodel.ViewModelFactory

/**
 * Creado por: Rodrigo Chávez (heinkel.66@gmail.com)
 * Fecha de creación: 21 de octubre del 2024
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var viewModel: LoginViewModel

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

        val userDao = AppDatabase.getDatabase(application).userDao()
        val repository = UserRepository(userDao)
        viewModel =
            ViewModelProvider(this, ViewModelFactory(repository, null))[LoginViewModel::class.java]


        viewModel.loginSucess.observe(this) { success ->
            loadingDialog.dismiss()

            if (success) {
                val intent = Intent(this, MovieActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView() {
        loadingDialog = LoadingDialog(this)

        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            loadingDialog.show()
            viewModel.authenticate(username, password)
        }
    }
}