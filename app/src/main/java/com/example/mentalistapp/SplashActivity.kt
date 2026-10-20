package com.example.mentalistapp

import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.mentalistapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.splashLogo.apply {
            scaleX = 0.6f
            scaleY = 0.6f
            alpha = 0f
            animate()
                .scaleX(1f).scaleY(1f).alpha(1f)
                .setDuration(600)
                .setInterpolator(OvershootInterpolator())
                .start()
        }

        binding.splashCaja.apply {
            alpha = 0f
            translationY = 40f
            animate()
                .alpha(1f).translationY(0f)
                .setStartDelay(600)
                .setDuration(500)
                .start()
        }

        binding.root.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 2500)
    }
}
