package com.albuquerque.data.local.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

internal class ReminderMovieWorker(
    val context: Context,
    val params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        // TODO
        /*NotificationHelper(context)
            .createNotification(
                inputData.getString("title").orEmpty(),
                inputData.getString("message").orEmpty()
            )*/
        return Result.success()
    }
}