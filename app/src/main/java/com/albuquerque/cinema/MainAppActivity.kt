package com.albuquerque.cinema

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.albuquerque.cinema.databinding.ActivityMainBinding
import com.albuquerque.common.deeplink.CinemaDeeplink
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainAppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tab = intent?.data?.getQueryParameter("tab")

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setupWithNavController(
            navHostFragment.navController
        )

        when (tab) {
            CinemaDeeplink.TAB_FAVORITES -> navHostFragment.navController.navigate(R.id.favorites_navigation)
            CinemaDeeplink.TAB_REMINDERS -> navHostFragment.navController.navigate(R.id.reminders_navigation)
            else -> Unit
        }

    }
}