package com.example.talana.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.talana.databinding.ActivityLoginBinding
import com.example.talana.databinding.ActivitySplashBinding
import com.example.talana.repository.RepositoryPreferences
import com.example.talana.ui.feed.FeedActivity
import com.example.talana.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val motionLayout = binding.motionLayout

        motionLayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                p0: MotionLayout?,
                p1: Int,
                p2: Int
            ) {
            }

            override fun onTransitionChange(
                p0: MotionLayout?,
                p1: Int,
                p2: Int,
                p3: Float
            ) {
            }

            override fun onTransitionCompleted(
                p0: MotionLayout?,
                p1: Int
            ) {
                if (RepositoryPreferences(applicationContext).isLoginKeep())
                    startActivity(Intent(this@SplashActivity, FeedActivity::class.java))
                else
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }

            override fun onTransitionTrigger(
                p0: MotionLayout?,
                p1: Int,
                p2: Boolean,
                p3: Float
            ) {
            }
        })
    }
}