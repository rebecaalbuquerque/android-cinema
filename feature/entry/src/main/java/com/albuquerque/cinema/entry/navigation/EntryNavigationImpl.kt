package com.albuquerque.cinema.entry.navigation

import android.content.Context
import android.content.Intent
import com.albuquerque.cinema.entry.presentation.MainActivity
import com.albuquerque.navigation.entry.EntryNavigation

class EntryNavigationImpl : EntryNavigation {

    override fun navigateToEntry(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}