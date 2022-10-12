package com.bignerdranch.android.network_gson_exercise.app.utils.logger

import android.util.Log
import com.bignerdranch.android.network_gson_exercise.app.utils.logger.Logger

object LogCatLogger : Logger {

    override fun log(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun error(tag: String, e: Throwable) {
        Log.e(tag, "Error!", e)
    }

}