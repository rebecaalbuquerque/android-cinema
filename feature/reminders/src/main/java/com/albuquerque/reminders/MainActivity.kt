package com.albuquerque.reminders

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.albuquerque.reminders.databinding.ActivityRemindersMainBinding

internal class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRemindersMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRemindersMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}