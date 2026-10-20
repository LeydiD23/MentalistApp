package com.example.mentalistapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mentalistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_portada,
                R.id.nav_personajes,
                R.id.nav_momentos,
                R.id.nav_juego,
                R.id.nav_acerca,
                R.id.nav_contratame -> binding.bottomNav.visibility =
                    android.view.View.VISIBLE
                else -> binding.bottomNav.visibility = android.view.View.GONE
            }
        }
    }
}
