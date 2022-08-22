package com.example.talana.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.example.talana.data.remote.api.RetrofitClient
import com.example.talana.data.remote.datasource.LoginDataSource
import com.example.talana.databinding.ActivityLoginBinding
import com.example.talana.repository.implementations.LoginRepositoryImpl
import com.example.talana.ui.feed.FeedActivity
import com.example.talana.utils.Resource
import com.example.talana.utils.isEmailValid


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var isAnimLogin = true
    private val viewModel by viewModels<LoginViewModel> {
        LoginViewModelFactory(
            LoginRepositoryImpl(
                LoginDataSource(RetrofitClient.apiservice)
            )
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.initPrefs(applicationContext)
        listenerEmail()
        listenerPassword()
        listenerButton()
    }

    private fun updateAnimation(anim: Int) {
        binding.lottieAnimationView.setAnimation(anim)
        binding.lottieAnimationView.playAnimation()
    }

    private fun listenerButton(){
        binding.buttonLogin.setOnClickListener {
            val email = binding.inputEmail.editText?.text.toString()
            val pass = binding.inputPassword.editText?.text.toString()

            if (isEmailValid(email) && pass.length > 4){
                doLogin(email,pass)
            }
            else if(!isEmailValid(email)) binding.inputEmail.error = "Email inválido"
            else if(pass.length <= 4) binding.inputPassword.error = "Password inválido"
        }
    }

    private fun listenerEmail(){
        binding.inputEmail.editText?.setOnClickListener {
            if (!isAnimLogin){
                updateAnimation(viewModel.LOGIN)
                isAnimLogin = true
            }
            binding.inputEmail.error = null
        }

        binding.inputEmail.editText?.addTextChangedListener {
            if(!isEmailValid(it.toString())) binding.inputEmail.error = "Email inválido"
            else binding.inputEmail.error = null
        }
    }

    private fun listenerPassword(){
        binding.inputPassword.editText?.setOnClickListener {
            if (!isAnimLogin){
                updateAnimation(viewModel.LOGIN)
                isAnimLogin = true
            }
            binding.inputPassword.error = null
        }

        binding.inputPassword.editText?.addTextChangedListener {
            if(it.toString().length <= 4) binding.inputPassword.error = "Password inválido"
            else binding.inputPassword.error = null
        }
    }

    private fun doLogin(username: String, password: String){
        viewModel.login(username, password).observe(this, Observer {
            when(it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    updateAnimation(viewModel.SUCCESS)
                    viewModel.saveApiToken(it.data.apiToken)
                    viewModel.saveKeepLogin(binding.checkKeepLogin.isChecked)
                    val myIntent = Intent(this, FeedActivity::class.java)
                    this.startActivity(myIntent)
                }
                is Resource.Failure -> {
                    updateAnimation(viewModel.FAILURE)
                    Log.d("MAS", it.exception.message!!)
                    it.exception.message?.let { e -> viewModel.showToast(e,this) }
                }
            }
        })
    }

}