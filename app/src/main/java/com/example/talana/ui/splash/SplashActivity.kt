package com.example.talana.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.talana.repository.RepositoryPreferences
import com.example.talana.ui.feed.FeedActivity
import com.example.talana.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (RepositoryPreferences(applicationContext).isLoginKeep())
            startActivity(Intent(this, FeedActivity::class.java))
        else
            startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}