package com.albuquerque.moviesupcoming.navigation

import android.content.Context
import android.content.Intent
import com.albuquerque.moviesupcoming.MainActivity
import com.albuquerque.navigation.moviesupcoming.MoviesUpcomingNavigation

class MoviesUpcomingNavigationImpl : MoviesUpcomingNavigation {

    override fun navigateToMoviesUpcoming(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}