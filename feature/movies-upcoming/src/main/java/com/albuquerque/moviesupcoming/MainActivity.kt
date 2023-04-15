package com.albuquerque.moviesupcoming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.albuquerque.moviesupcoming.databinding.ActivityMoviesUpcomingMainBinding

internal class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesUpcomingMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesUpcomingMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}