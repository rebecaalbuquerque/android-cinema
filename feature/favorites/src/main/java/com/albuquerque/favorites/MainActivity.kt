package com.albuquerque.favorites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.albuquerque.favorites.databinding.ActivityFavoritesMainBinding

internal class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}