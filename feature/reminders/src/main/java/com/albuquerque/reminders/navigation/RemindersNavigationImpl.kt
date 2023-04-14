package com.albuquerque.reminders.navigation

import android.content.Context
import android.content.Intent
import com.albuquerque.navigation.reminders.RemindersNavigation
import com.albuquerque.reminders.RemindersMainActivity

class RemindersNavigationImpl : RemindersNavigation {

    override fun navigateToReminders(context: Context) {
        val intent = Intent(context, RemindersMainActivity::class.java)
        context.startActivity(intent)
    }
}