package com.albuquerque.favorites.navigation

import android.content.Context
import android.content.Intent
import com.albuquerque.favorites.FavoritesMainActivity
import com.albuquerque.navigation.favorites.FavoritesNavigation

class FavoritesNavigationImpl : FavoritesNavigation {

    override fun navigateToFavorites(context: Context) {
        val intent = Intent(context, FavoritesMainActivity::class.java)
        context.startActivity(intent)
    }
}