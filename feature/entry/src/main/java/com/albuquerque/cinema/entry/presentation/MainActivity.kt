package com.albuquerque.cinema.entry.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.albuquerque.cinema.entry.databinding.ActivityEntryMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntryMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // adb shell am start -a android.intent.action.VIEW com.albuquerque.cinema/com.albuquerque.cinema.entry.presentation.MainActivity
    }
}